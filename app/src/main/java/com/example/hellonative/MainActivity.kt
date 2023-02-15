package com.example.hellonative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.home).setOnClickListener {
            startActivity(FlutterActivity.createDefaultIntent(this))
        }
        findViewById<TextView>(R.id.second).setOnClickListener {
            startActivity(FlutterActivity.withNewEngine().initialRoute("/second").build(this))
        }
    }
}