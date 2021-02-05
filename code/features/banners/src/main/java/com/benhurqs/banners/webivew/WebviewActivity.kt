package com.benhurqs.banners.webivew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benhurqs.banners.R
import com.benhurqs.base.actions.Actions
import kotlinx.android.synthetic.main.banner_webview_activity.*

class WebviewActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banner_webview_activity)
    }

    override fun onStart() {
        super.onStart()

        if(intent.hasExtra(Actions.BANNER_URL)){
            openWeview(intent.extras?.getString(Actions.BANNER_URL))
        }else{
            this.finish()
        }
    }

    private fun openWeview(url: String?){
        webview.loadUrl(url)
    }

}