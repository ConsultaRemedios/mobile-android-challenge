package br.com.weslleymaciel.gamesecommerce.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.CartItem
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.common.utils.CartHelper
import br.com.weslleymaciel.gamesecommerce.common.utils.numberToPrice
import br.com.weslleymaciel.gamesecommerce.view.adapters.CartAdapter
import br.com.weslleymaciel.gamesecommerce.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import org.jetbrains.anko.toast
import java.lang.String


class CartActivity: AppCompatActivity(), CartAdapter.DataChangedListener {

    private val viewModel = GamesViewModel()

    private val observerCheckout = Observer<Boolean?> {
        it?.let {
            if (it) {
                CartHelper.removeAll()
                configureCartList(CartHelper.getCartList())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue)
        }

        ivBack.setOnClickListener { onBackPressed() }
        btnCheckout.setOnClickListener {
            if (CartHelper.getCartList().size <= 0){
                toast(resources.getString(R.string.checkout_empty_cart))
            }else{
                rvCart.visibility = View.GONE
                llLoad.visibility = View.VISIBLE
                llListEmpty.visibility = View.GONE
                viewModel.checkout().observe(this, observerCheckout)
            }
        }

        configureCartList(CartHelper.getCartList())
    }

    private fun configureCartList(cartItems: List<CartItem>){
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = CartAdapter(cartItems, this)
        rvCart.visibility = View.VISIBLE
        llLoad.visibility = View.GONE
        llListEmpty.visibility = View.GONE
        calculateTotalPrice()

        if(CartHelper.getCartList().size <= 0){
            rvCart.visibility = View.GONE
            llLoad.visibility = View.GONE
            llListEmpty.visibility = View.VISIBLE
        }
    }

    private fun calculateTotalPrice(){
        var count = 0
        var total = 0.0
        var totalDiscount = 0.0
        val shippingTax = 10
        var shippingTotal = 0

        for (cartItem in CartHelper.getCartList()){
            total += cartItem.price.toFloat() * cartItem.count.toInt()
            totalDiscount += (cartItem.price.toFloat() - cartItem.discount.toFloat()) * cartItem.count.toInt()
            shippingTotal += shippingTax * cartItem.count.toInt()
            count += cartItem.count.toInt()
        }

        if (totalDiscount > 250.0){
            shippingTotal = 0
        }

        tvProducts.text = String.format(resources.getString(R.string.products), count.toString())
        tvTotal.text = numberToPrice(total)
        tvTotalWithDiscount.text = numberToPrice(totalDiscount + shippingTotal)
    }

    override fun dataChanged(view: View?, result: Boolean) {
        if (result){
            configureCartList(CartHelper.getCartList())
        }
    }
}