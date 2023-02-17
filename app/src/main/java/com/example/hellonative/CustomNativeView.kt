package com.example.hellonative

import android.content.Context
import android.view.View
import android.widget.TextView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class CustomNativeView(context: Context?):PlatformView{
    private val showTextView:TextView = TextView(context)
    init {
        showTextView.text = "hello this is a android view"
    }
    override fun getView(): View {
        return showTextView
    }

    override fun dispose() {

    }

}

class  NativeViewFactory(var message:BinaryMessenger):PlatformViewFactory(StandardMessageCodec.INSTANCE){
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        return CustomNativeView(context)
    }
}
