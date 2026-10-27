package com.ghonti.app

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView

class MainActivity : Activity() {
    private lateinit var web: WebView

    companion object {
        @Volatile
        var foreground = false
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        setContentView(web)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.mediaPlaybackRequiresUserGesture = false
        web.webChromeClient = WebChromeClient()
        web.addJavascriptInterface(Bridge(), "Android")
        web.loadUrl("file:///android_asset/index.html")
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }

    override fun onResume() {
        super.onResume()
        foreground = true
        web.evaluateJavascript("if(window.nativeFired)nativeFired()", null)
    }

    override fun onPause() {
        foreground = false
        super.onPause()
    }

    inner class Bridge {
        @JavascriptInterface
        fun sync(json: String) {
            AlarmScheduler.saveAndSchedule(applicationContext, json)
        }

        @JavascriptInterface
        fun takeFired(): String = AlarmScheduler.takeFired(applicationContext)

        @JavascriptInterface
        fun setBars(css: String) {
            val n = Regex("\\d+").findAll(css).map { it.value.toInt() }.toList()
            if (n.size < 3) return
            runOnUiThread {
                val c = Color.rgb(n[0], n[1], n[2])
                window.statusBarColor = c
                window.navigationBarColor = c
                val light = (0.299 * n[0] + 0.587 * n[1] + 0.114 * n[2]) > 150
                var mask = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (Build.VERSION.SDK_INT >= 26) mask = mask or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                val f = window.decorView.systemUiVisibility
                window.decorView.systemUiVisibility = if (light) f or mask else f and mask.inv()
            }
        }
    }
}
