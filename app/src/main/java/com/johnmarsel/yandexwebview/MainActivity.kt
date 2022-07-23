package com.johnmarsel.yandexwebview

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentResultListener

private const val URL = "https://yandex.ru/"
private const val EXIT_DIALOG = "ExitDialog"

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.web_view)

        setUpWebview()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebview() {
        webView.webViewClient = WebViewClient()
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(URL)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        ExitDialogFragment.newInstance().show(supportFragmentManager, EXIT_DIALOG)
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)

        return super.onKeyDown(keyCode, event)
    }
}