package com.example.hellonative

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * 给原生注入 FlutterView 的方式来引入 FlutterView
 */
class FlutterViewActivity :AppCompatActivity() {
    lateinit var flutterEngine :FlutterEngine
    lateinit var flutterView:FlutterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(flutterView)


        lifecycle.addObserver(object:DefaultLifecycleObserver{
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                flutterEngine.lifecycleChannel.appIsResumed()
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                flutterEngine.lifecycleChannel.appIsInactive()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                flutterEngine.lifecycleChannel.appIsDetached()
                flutterEngine.destroy()
            }
        })
    }

    private fun init() {
        flutterView = FlutterView(this)
        flutterEngine = FlutterEngine(this)
        flutterEngine.navigationChannel.setInitialRoute("/second")
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        );
        flutterView.attachToFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,"flutter_channel").setMethodCallHandler(object:MethodChannel.MethodCallHandler{
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                   when(call.method){
                       "getPlatformVersion" -> {
                           result.success("android ${Build.VERSION.SDK_INT}")
                       }
                       "jump2FlutterViewPage" -> {
                           startActivity(Intent(this@FlutterViewActivity,FlutterViewActivity::class.java))
                       }
                       else -> {
                           result.notImplemented()
                       }
                   }
            }
        })
    }

}