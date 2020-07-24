package com.benhurqs.checkout.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.base.utils.Utils
import com.benhurqs.checkout.R
import com.benhurqs.checkout.data.CartRepository
import com.benhurqs.checkout.data.CartUpdateListener
import com.benhurqs.checkout.presentation.adapter.CheckoutAdapter
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Cart
import kotlinx.android.synthetic.main.checkout_activity.*

class CheckoutActivity : AppCompatActivity(), CartUpdateListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)


        cart_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cart_recyclerview.adapter = CheckoutAdapter(CartRepository.getInstance().getList())

    }

    override fun onResume() {
        super.onResume()
        CartRepository.getInstance().addObserver(this)
        onUpdate()
    }

    override fun onPause() {
        super.onPause()
        CartRepository.getInstance().removeObserver(this)
    }

    fun onClickBack(v: View?){
        this.finish()
    }

    fun onClickBuy(v: View?){
        NetworkRepository.checkout(
            onStart = {onLoading()},
            onSuccess = {onSuccess()},
            onFailure = {onFailure(it)},
            onFinish = {onFinish()}
        )
    }

    override fun onUpdate() {
        checkout_total_title.text = getString(R.string.produtos_qtd, CartRepository.getInstance().getList().count())
        checkout_total_value.text = Utils.formatPrice(CartRepository.getInstance().total)

        if(CartRepository.getInstance().freight == CartRepository.FREIGHT_FREE){
            checkout_freight_content.visibility = View.GONE
        }else{
            checkout_freight_content.visibility = View.VISIBLE
            checkout_total_freight_value.text = Utils.formatPrice(CartRepository.getInstance().freight)
        }

        checkout_total_price.text = Utils.formatPrice(CartRepository.getInstance().freight + CartRepository.getInstance().total)

        if(CartRepository.getInstance().getList().count() > 0 ){
            checkout_button.background = resources.getDrawable(R.drawable.green_button)
            checkout_button.isClickable = true
        }else{
            checkout_button.background = resources.getDrawable(R.drawable.gray_button)
            checkout_button.isClickable = false
        }
    }

    private fun onLoading(){
        checkout_progress.visibility = View.VISIBLE
        checkout_button.visibility = View.GONE
    }

    private fun onSuccess(){
        CartRepository.getInstance().clearCart()
        Toast.makeText(this, "Compra finalizada com sucesso", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    private fun onFailure(error: String?){
        Toast.makeText(this, "Error ao finalizar compra. Tente novamente!", Toast.LENGTH_SHORT).show()

    }

    private fun onFinish(){
        checkout_progress.visibility = View.GONE
        checkout_button.visibility = View.VISIBLE

    }
}