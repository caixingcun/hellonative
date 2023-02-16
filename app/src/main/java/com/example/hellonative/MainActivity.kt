package com.example.hellonative

import android.os.Build
import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        Log.d("tag","configureFlutterEngine")
        var channel =   MethodChannel(flutterEngine.dartExecutor.binaryMessenger,"flutter_channel")
        channel.setMethodCallHandler { call, result ->
            // flutter 调原生
            if(call.method == "getPlatformVersion"){
                // 原生回复flutter
                result.success("android ${Build.VERSION.SDK_INT}")
            }else {
                result.notImplemented()
            }
        }
    }

}