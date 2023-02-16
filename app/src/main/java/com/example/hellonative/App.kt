package com.example.hellonative

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        initFlutterEngine()
    }

    private fun initFlutterEngine() {
        var flutterEngine = FlutterEngine(this)
        flutterEngine.navigationChannel.setInitialRoute("/third")
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put("/third",flutterEngine)

    }
}