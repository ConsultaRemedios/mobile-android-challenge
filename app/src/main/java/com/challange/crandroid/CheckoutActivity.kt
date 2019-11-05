package com.challange.crandroid

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challange.crandroid.adapter.CheckoutItensAdapter
import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.CartItem
import com.challange.crandroid.singleton.Cart
import com.challange.crandroid.utils.GenericUtils.Companion.brazilianNumberFormat
import kotlinx.android.synthetic.main.activity_checkout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        buttonFinalizarCompra.setOnClickListener(clickFinalizarCompra)
        continuarComprando.setOnClickListener(clickContinuarComprando)

        Cart.calcularCarrinho()
        cartPrice.text = brazilianNumberFormat().format(Cart.valorTotal)
        freightPrice.text = brazilianNumberFormat().format(Cart.valorFrete)

        val viewManager = LinearLayoutManager(this);
        val checkoutItemsAdapter = CheckoutItensAdapter(this, Cart.itens as ArrayList<CartItem>)

        findViewById<RecyclerView>(R.id.cartItensRecyclerView).apply {
            adapter = checkoutItemsAdapter
            layoutManager = viewManager
        }
    }

    private val clickFinalizarCompra = View.OnClickListener {
        val progressDialog = ProgressDialog.show(this, null, "Aguarde, estamos finalizando seu pedido", true)

        GameCheckoutServiceInitializer().gameCheckoutService().checkout(Cart)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    progressDialog.dismiss()
                    Cart.limparCarrinho()
                    navigateToConfirmation()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }

    private val clickContinuarComprando = View.OnClickListener {
        val intent = Intent(this, GamesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToConfirmation() {
        val intent = Intent(this, ConfirmationActivity::class.java)
        startActivity(intent)
    }
}
