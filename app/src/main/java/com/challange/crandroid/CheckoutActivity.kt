package com.challange.crandroid

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challange.crandroid.adapter.CheckoutItemsAdapter
import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.CartItem
import com.challange.crandroid.singleton.Cart
import com.challange.crandroid.utils.GenericUtils.Companion.brazilianNumberFormat
import kotlinx.android.synthetic.main.activity_checkout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.challange.crandroid.widget.NumberPickerDialog
import android.widget.Toast


class CheckoutActivity : AppCompatActivity(), NumberPicker.OnValueChangeListener, CheckoutItemsAdapter.OnQuantityTapListener {

    private var mAdapter: CheckoutItemsAdapter? = null
    private var mSelectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        buttonFinalizarCompra.setOnClickListener(clickFinalizarCompra)
        continuarComprando.setOnClickListener(clickContinuarComprando)

        updateCartValues()

        val viewManager = LinearLayoutManager(this)
        mAdapter = CheckoutItemsAdapter(this, Cart.itens as ArrayList<CartItem>, this)

        findViewById<RecyclerView>(R.id.cartItensRecyclerView).apply {
            adapter = mAdapter
            layoutManager = viewManager
        }
    }

    override fun onQuantityTap(position: Int) {
        mSelectedPosition = position
        showNumberPicker(Cart.itens[position].quantidade)
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        if (newVal == 0)
            Cart.itens.removeAt(mSelectedPosition)
        else
            Cart.itens[mSelectedPosition].quantidade = newVal

        updateCartValues()
        mAdapter?.notifyDataSetChanged()
    }

    private val clickFinalizarCompra = View.OnClickListener {
        val progressDialog = ProgressDialog.show(this, null, "Aguarde, estamos finalizando seu pedido", true)

        GameCheckoutServiceInitializer().gameCheckoutService().checkout(Cart)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        progressDialog.dismiss()
                        Cart.limparCarrinho()
                        navigateToConfirmation()
                    } else {
                        Toast.makeText(parent, "Eita miseria, tem algo errado com seu carrinho macho", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(parent, "Oh no! Não conseguimos enviar seus games marotos =/", Toast.LENGTH_LONG).show()
                }
            })
    }

    private val clickContinuarComprando = View.OnClickListener {
        val intent = Intent(this, GamesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun updateCartValues() {
        Cart.calcularCarrinho()
        cartPrice.text = brazilianNumberFormat().format(Cart.valorTotal)
        freightPrice.text = brazilianNumberFormat().format(Cart.valorFrete)
    }

    private fun showNumberPicker(initValue: Int) {
        val newFragment = NumberPickerDialog(initValue)
        newFragment.setValueChangeListener(this)
        newFragment.show(supportFragmentManager, "time picker")
    }

    private fun navigateToConfirmation() {
        val intent = Intent(this, ConfirmationActivity::class.java)
        startActivity(intent)
    }
}
