package br.com.challenge.consultaremedios


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.consultaremedios.adapter.CartItemAdapter
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.db.viewmodel.CartViewModel
import br.com.challenge.consultaremedios.utils.GenericUtils.Companion.brazilianNumberFormat
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), CartItemAdapter.GameQuantityAdapterListener {

    private lateinit var cartViewModel: CartViewModel
    private var mCartItemAdapter: CartItemAdapter? = null
    var cartItems: List<CartItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        showCustomUI()
        initData()
    }

    private fun showCustomUI() {
        setSupportActionBar(cart_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val recyclerView = findViewById<RecyclerView>(R.id.cart_items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mCartItemAdapter = CartItemAdapter(this, cartItems.orEmpty(), this)
        recyclerView.adapter = mCartItemAdapter
    }

    private fun initData() {
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.cartItems.observe(this, { item ->
            mCartItemAdapter?.items = item
            mCartItemAdapter?.notifyDataSetChanged()

            findViewById<TextView>(R.id.cart_items_sum).apply {
                text = getString(R.string.cart_items_sum, item.map { it.quantity }.sum())
            }
            findViewById<TextView>(R.id.price_sum).apply {
                text = brazilianNumberFormat().format(item.map { it.unitPrice * it.quantity }.sum())
            }
            findViewById<TextView>(R.id.price_sum_with_discount).apply {
                text = brazilianNumberFormat().format(item.map { it.unitPriceWithDiscount * it.quantity }.sum())
            }
        }
        )
    }

    override fun onRemoveQuantityListener(position: Int) {
        val item = mCartItemAdapter?.items?.get(position)
        if (item == null || item.quantity == 0)
            return

        item.quantity = item.quantity.minus(1)
        cartViewModel.update(item)
    }

    override fun onAddQuantityListener(position: Int) {
        val item = mCartItemAdapter?.items?.get(position)
        item?.quantity = item?.quantity?.plus(1)!!
        cartViewModel.update(item)
    }

    override fun onRemoveGameListener(position: Int) {
        val item = mCartItemAdapter?.items?.get(position)
        item?.let { cartViewModel.delete(it) }
    }
}