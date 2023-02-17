package com.example.hellonative

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

//原生引入FlutterFragment
class MyContainFlutterFragmentActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contain_flutter_fragment)
        findViewById<Button>(R.id.btn).setOnClickListener {
            var content = findViewById<EditText>(R.id.et).text.toString().trim()
            nativeChannel.invokeMethod("nativeSend",content)
        }
        initFlutterFragment()
    }

    lateinit var  nativeChannel :MethodChannel

    private fun initFlutterFragment() {
        var flutterFragment = FlutterFragment.withCachedEngine("/third").build<FlutterFragment>()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl,flutterFragment)
            .commit()

        var engine = FlutterEngineCache.getInstance().get("/third")
        //初始化通道
        MethodChannel(engine?.dartExecutor?.binaryMessenger,"flutter_to_native").setMethodCallHandler(object:MethodChannel.MethodCallHandler{
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                if(call.method == "flutterSend"){
                    findViewById<TextView>(R.id.tv_content).append(call.arguments.toString())
                }
            }
        })
        engine?.dartExecutor?.binaryMessenger?.let { binaryMessager->
            engine?.platformViewsController.registry.registerViewFactory("android_native_view",NativeViewFactory(binaryMessager))
        }

        nativeChannel = MethodChannel(engine?.dartExecutor?.binaryMessenger,"native_to_flutter")

        // 生命周期绑定
        lifecycle.addObserver(object :DefaultLifecycleObserver{
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                flutterFragment.onStart()
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                flutterFragment.onStop()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                flutterFragment.onDestroy()
            }
        })
    }
}