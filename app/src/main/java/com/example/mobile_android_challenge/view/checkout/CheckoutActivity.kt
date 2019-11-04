package com.example.mobile_android_challenge.view.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.view.games_list.GamesActivity
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.custom_toobar.view.*

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_purchase_message.setText(R.string.purchase_title)
        purchase_toolbar.img_cart.visibility = View.GONE
        purchase_toolbar.img_search.visibility = View.GONE

        tv_back_to_purchase.setOnClickListener {
            val intent = Intent(this.baseContext, GamesActivity::class.java)
            this.baseContext.startActivity(intent)
        }
    }
}
