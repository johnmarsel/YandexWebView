package com.johnmarsel.yandexwebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity


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
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            loadUrl(URL)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            ExitDialogFragment.newInstance().show(supportFragmentManager, EXIT_DIALOG)
            false
        }
    }
}