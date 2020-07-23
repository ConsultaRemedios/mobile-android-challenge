package com.benhurqs.checkout.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.checkout.R
import com.benhurqs.checkout.data.CartRepository
import com.benhurqs.checkout.presentation.adapter.CheckoutAdapter
import com.benhurqs.network.entities.Cart
import kotlinx.android.synthetic.main.checkout_activity.*

class CheckoutActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)


        cart_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cart_recyclerview.adapter = CheckoutAdapter(CartRepository.getInstance().getList())


    }

    fun onClickBack(v: View?){
        this.finish()
    }

    fun onClickBuy(v: View?){

    }
}