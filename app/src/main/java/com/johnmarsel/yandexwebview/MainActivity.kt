package com.johnmarsel.yandexwebview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.johnmarsel.yandexwebview.databinding.ActivityMainBinding


private const val URL = "https://yandex.ru/"
private const val EXIT_DIALOG = "ExitDialog"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView() {
        binding.webView.webViewClient = object: WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                url?.let { it ->
                    if (it.contains("https://yandex.ru/maps")) {
                        val launchIntent =
                            packageManager.getLaunchIntentForPackage("ru.yandex.yandexmaps")
                        launchIntent?.let { intent: Intent -> startActivity(intent) }
                    } else if (it.contains("https://yandex.ru/pogoda")) {
                        val launchIntent =
                            packageManager.getLaunchIntentForPackage("ru.yandex.weatherplugin")
                        launchIntent?.let { intent: Intent -> startActivity(intent) }
                    } else {
                        super.doUpdateVisitedHistory(view, url, isReload)
                    }
                }
            }
        }
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.webView, true)
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            val prefs = getSharedPreferences(packageName, MODE_PRIVATE)
            val lastUrl = prefs.getString("lastUrl", "") ?: ""
            if (lastUrl.isEmpty()) {
                loadUrl(URL)
            } else {
                loadUrl(lastUrl)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            ExitDialogFragment.newInstance().show(supportFragmentManager, EXIT_DIALOG)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefs = getSharedPreferences(packageName, MODE_PRIVATE)
        val edit = prefs.edit()
        edit.apply {
            putString("lastUrl", binding.webView.url)
            commit()
        }
    }
}