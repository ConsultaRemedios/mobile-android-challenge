package com.benhurqs.checkout.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.benhurqs.checkout.R

class CheckoutActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)
    }

    fun onClickBack(v: View?){
        this.finish()
    }
}