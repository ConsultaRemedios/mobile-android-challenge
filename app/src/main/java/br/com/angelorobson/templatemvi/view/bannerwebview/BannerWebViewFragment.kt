package br.com.angelorobson.templatemvi.view.bannerwebview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.angelorobson.templatemvi.R
import kotlinx.android.synthetic.main.fragment_banner_web_view.*


class BannerWebViewFragment : Fragment(R.layout.fragment_banner_web_view) {

    private val args: BannerWebViewFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onStart() {
        super.onStart()
        val webView = banner_web_view
        webView.loadUrl(args.url)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        webView.webViewClient = WebViewClient()

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (banner_web_view != null) {
                    banner_web_view.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (banner_web_view != null) {
                    banner_web_view.visibility = View.GONE
                }
            }
        }

        webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action != KeyEvent.ACTION_DOWN) {
                    return true
                }
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack()

                    } else {
                        activity?.onBackPressed();
                    }
                }

                return true
            }

        })

    }

}