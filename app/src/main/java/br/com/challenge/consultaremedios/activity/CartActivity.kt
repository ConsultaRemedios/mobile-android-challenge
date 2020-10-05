package br.com.challenge.consultaremedios.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.consultaremedios.R
import br.com.challenge.consultaremedios.adapter.CartItemAdapter
import br.com.challenge.consultaremedios.api.mobiletest.Endpoints
import br.com.challenge.consultaremedios.api.mobiletest.ApiService
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.db.viewmodel.CartViewModel
import br.com.challenge.consultaremedios.utils.GenericUtils.Companion.brazilianNumberFormat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity(), CartItemAdapter.GameQuantityAdapterListener {

    private val api = ApiService.buildService(Endpoints::class.java)
    private var cartItemAdapter: CartItemAdapter? = null
    private var cartItems: List<CartItem>? = null
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initView()
        initData()
    }

    private fun initView() {
        setSupportActionBar(cart_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        cartItemAdapter = CartItemAdapter(this, cartItems.orEmpty(), this)
        val recyclerView = findViewById<RecyclerView>(R.id.cart_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartItemAdapter
    }

    private fun initData() {
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.cartItems.observe(this, { items ->
            cartItemAdapter?.items = items
            cartItemAdapter?.notifyDataSetChanged()

            val quantity = items.map { it.quantity }.sum()
            val total = items.map { it.unitPrice * it.quantity }.sum()
            val totalWithDiscount = items.map { it.unitPriceWithDiscount * it.quantity }.sum()
            val shippingValue = if (totalWithDiscount > 250.0) 0.0
            else 10.0 * quantity

            findViewById<TextView>(R.id.cart_items_sum).apply {
                text = getString(R.string.text_items_sum, quantity)
            }
            findViewById<TextView>(R.id.price_sum).apply {
                text = brazilianNumberFormat().format(total)
            }
            findViewById<TextView>(R.id.price_sum_with_discount).apply {
                text = brazilianNumberFormat().format(totalWithDiscount.plus(shippingValue))
            }
            findViewById<TextView>(R.id.shipping_value).apply {
                text = if (shippingValue == 0.0 && totalWithDiscount > 0.0) getString(R.string.text_free)
                else brazilianNumberFormat().format(shippingValue)
            }
        }
        )
    }

    override fun onRemoveQuantityListener(position: Int) {
        val item = cartItemAdapter?.items?.get(position)
        if (item == null || item.quantity == 0)
            return

        item.quantity = item.quantity.minus(1)
        cartViewModel.update(item)
    }

    override fun onAddQuantityListener(position: Int) {
        val item = cartItemAdapter?.items?.get(position)
        item?.quantity = item?.quantity?.plus(1)!!
        cartViewModel.update(item)
    }

    override fun onRemoveGameListener(position: Int) {
        val item = cartItemAdapter?.items?.get(position)
        item?.let { cartViewModel.delete(it) }
    }

    fun checkout(view: View) {
        val items = cartItemAdapter?.items.orEmpty()
        if (items.isEmpty() || items.map { it.quantity }.sum() == 0) {
            Snackbar.make(view, getString(R.string.message_warn_empty_cart), Snackbar.LENGTH_LONG).show()
            return
        }

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE

        // Simulate long process
        GlobalScope.launch {
            delay(3000L)

            api.postCheckout(items).enqueue(object : Callback<Void> {
                @SuppressLint("CheckResult")
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@CartActivity,
                            getString(R.string.message_success_checkout),
                            Toast.LENGTH_LONG
                        ).show()

                        cartViewModel.deleteALl(items)
                        Intent(this@CartActivity, MainActivity::class.java).apply {
                            navigateUpTo(this@apply)
                        }
                    }
                    else
                        Toast.makeText(this@CartActivity, getString(R.string.message_error_api_request), Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@CartActivity, getString(R.string.message_error_api_request), Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }
            })
        }
    }
}