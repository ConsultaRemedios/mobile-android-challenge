package java.games.ecommerce.main.ui.activity.shoppingcart

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_shoppingcart.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.ShoppingGame
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.asCurrency
import java.games.ecommerce.utils.observe
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
        productamount_cart.text = "Produtos(" + totalAmount.toString() + ")"
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

}
