package com.games.ecommerce.main.ui.activity.shoppingcart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.ShoppingGame
import com.games.ecommerce.main.ui.activity.gamelist.ListActivity
import com.games.ecommerce.utils.ViewModelFactory
import com.games.ecommerce.utils.asCurrency
import com.games.ecommerce.utils.observe
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_shoppingcart.*
import javax.inject.Inject

class ShoppingCartActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ShoppingCartViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingcart)
        setupView()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        finish_shop_btn.setOnClickListener {
            viewModel.checkout()
        }
    }


    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.fetchData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupObservers() {
        observe(viewModel.shoppingGames) {
            addGames(it)
            updateTotalPrice(it)
            updateTotalAmount(it)
            updatePrice(it)
        }
        observe(viewModel.statusDB) {
            viewModel.fetchData()
        }
        observe(viewModel.checkoutSuccess) {
            if (it) {
                showSuccessDialog(getString(R.string.message_success_finish))
                viewModel.clearCart()
            } else showErrorDialog(getString(R.string.message_error_finish))
        }
    }

    private fun updateTotalPrice(shoppingGames: List<ShoppingGame>) {
        val totalPrice = viewModel.getPriceWithCharges(shoppingGames)
        totalprice_cart.text = totalPrice.asCurrency()
    }

    private fun updatePrice(shoppingGames: List<ShoppingGame>) {
        val price = viewModel.getPrice(shoppingGames)
        price_cart.text = price.asCurrency()
    }

    private fun updateTotalAmount(shoppingGames: List<ShoppingGame>) {
        val totalAmount = viewModel.getTotalAmount(shoppingGames)
        productamount_cart.text = String.format("Produtos (%s)", totalAmount.toString())
    }


    private fun addGames(games: List<ShoppingGame>) {
        recyclerView_cart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter =
                ShoppingCartAdapter(games, {
                    viewModel.addOnCart(it)
                }, {
                    viewModel.removeFromCart(it)
                }, {
                    viewModel.removeAllFromCart(it)
                })
        }
    }

    private fun showErrorDialog(error: String) {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            setMessage(error)
            setTitle(R.string.warning_title)
            setPositiveButton(
                getString(R.string.warning_tryagain_button)
            ) { _, _ ->
                viewModel.checkout()
            }
            setNegativeButton(
                getString(R.string.warning_cancel_button)
            ) { _, _ ->
                viewModel.fetchData()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun showSuccessDialog(error: String) {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            setMessage(error)
            setTitle(R.string.warning_title)
            setPositiveButton(
                getString(R.string.warning_ok_button)
            ) { _, _ ->
                startMainActivity()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this@ShoppingCartActivity, ListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
