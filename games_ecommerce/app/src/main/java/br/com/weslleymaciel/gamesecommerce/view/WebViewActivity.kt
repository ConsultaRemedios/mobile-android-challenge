package br.com.weslleymaciel.gamesecommerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.weslleymaciel.gamesecommerce.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity: AppCompatActivity() {
    private val url by lazy {
        intent?.extras?.getString("URL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        WebView.settings.domStorageEnabled = true
        WebView.settings.javaScriptEnabled = true
        WebView.settings.domStorageEnabled = true;
        WebView.settings.loadWithOverviewMode = true;
        WebView.settings.useWideViewPort = true;
        WebView.settings.builtInZoomControls = true;
        WebView.settings.displayZoomControls = false;
        WebView.settings.defaultTextEncodingName = "utf-8";
        WebView.loadUrl(url!!)
    }
}