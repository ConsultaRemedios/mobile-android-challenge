package com.example.mobile_android_challenge.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.model.ItemCart
import com.example.mobile_android_challenge.view.cart.adapter.CartAdapter
import com.example.mobile_android_challenge.view.checkout.CheckoutActivity
import com.example.mobile_android_challenge.view_model.CartViewModel
import com.example.mobile_android_challenge.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_card_bottom.*
import kotlinx.android.synthetic.main.cart_card_bottom.view.*
import javax.inject.Inject


class CartActivity : AppCompatActivity() {
    @Inject
    lateinit var cartVMFactory: ViewModelFactory<CartViewModel>

    private val cartViewModel by lazy {
        ViewModelProviders.of(this, cartVMFactory)[CartViewModel::class.java]
    }

    private val cartObserver = Observer<List<ItemCart>>(::onCartFetched)
    private val cartCheckoutObserver = Observer<String>(::onCheckout)

    private lateinit var adapter: CartAdapter
    var layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartViewModel.data.observe(this, cartObserver)
        cartViewModel.fetchCartItems(this.baseContext)
    }



    private fun onCartFetched(list: List<ItemCart>?) {
        if (list != null) {
            updateAdapter(list)
        }
    }

    private fun updateAdapter(list: List<ItemCart>) {
        rc_cart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rc_cart.setHasFixedSize(true)
        adapter = CartAdapter()
        adapter.update(list)

        view_cart_bottom.tv_continue_purchase.setOnClickListener {
            onBackPressed()
        }

        btn_finish_purchase.setOnClickListener {
            cartViewModel.finishCart()
            val intent = Intent(this.baseContext, CheckoutActivity::class.java)
            this.baseContext.startActivity(intent)
        }
        rc_cart.adapter = adapter
        setPrices(list)
        progress_bar.visibility = View.GONE
    }

    private fun setPrices(items: List<ItemCart>) {
        var totalPrice = 0.0
        items.map { itemCart -> totalPrice += (itemCart.price * itemCart.quantity) }
        val totalPriceFormatter:Double = Math.round(totalPrice * 1000.0) / 1000.0
        view_cart_bottom.tv_cart_price.text = getString(R.string.item_price, totalPriceFormatter.toString())
        if (totalPrice < 250) {
            var totalAmount = 0
            items.map { itemCart -> totalAmount = (itemCart.quantity + totalAmount) }
            customerInformation.tv_cart_price.text = getString(R.string.item_price, (totalAmount * 10).toString())
            return
        }
        customerInformation.tv_cart_price.text = getString(R.string.free_label)
    }

    private fun onCheckout(s: String?) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        val intent = Intent(this.baseContext, CheckoutActivity::class.java)
        this.baseContext.startActivity(intent)
    }
}
