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

        WebView.loadUrl(url!!)
    }
}