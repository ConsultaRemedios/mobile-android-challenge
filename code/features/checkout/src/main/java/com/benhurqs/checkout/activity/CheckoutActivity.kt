package com.benhurqs.checkout.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.checkout.R
import com.benhurqs.checkout.adapter.CheckoutAdapter
import com.benhurqs.network.entities.Cart
import kotlinx.android.synthetic.main.checkout_activity.*

class CheckoutActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)

        val list = ArrayList<Cart>()
        list.add(Cart().apply {
            this.image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            this.id = 1
            this.title = "Mario Galaxy"
            this.price = 350.0
            this.discount = 100.0
            this.qtd = 1
        })

        list.add(Cart().apply {
            this.image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            this.id = 1
            this.title = "The Legend Of Zelda Breath of The Wild"
            this.price = 350.0
            this.discount = 100.0
            this.qtd = 1
        })

        list.add(Cart().apply {
            this.image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            this.id = 1
            this.title = "The Legend Of Zelda Breath of The Wild"
            this.price = 350.0
            this.discount = 100.0
            this.qtd = 1
        })

        list.add(Cart().apply {
            this.image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            this.id = 1
            this.title = "The Legend Of Zelda Breath of The Wild"
            this.price = 350.0
            this.discount = 100.0
            this.qtd = 1
        })

        list.add(Cart().apply {
            this.image = "https://switch-brasil.com/wp-content/uploads/2020/02/Zelda-Breath-of-the-Wild_Keyart.jpg"
            this.id = 1
            this.title = "The Legend Of Zelda Breath of The Wild"
            this.price = 350.0
            this.discount = 100.0
            this.qtd = 1
        })

        cart_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cart_recyclerview.adapter = CheckoutAdapter(list)


    }

    fun onClickBack(v: View?){
        this.finish()
    }

    fun onClickBuy(v: View?){

    }
}