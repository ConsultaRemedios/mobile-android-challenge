package mazer.arthur.gamingshop.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cart.*
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.remote.ApiHelper
import mazer.arthur.gamingshop.data.remote.RetrofitHelper
import mazer.arthur.gamingshop.utils.ViewModelFactory
import mazer.arthur.gamingshop.domain.models.Status
import mazer.arthur.gamingshop.view.adapter.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var viewModel: CartViewModel
    private var cartAdapter = CartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupViewModel()
        setupCartRecyclerView()
        registerObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            ViewModelFactory(
                ApiHelper(
                    RetrofitHelper.api
                ), applicationContext
            )
        ).get(CartViewModel::class.java)
    }

    private fun setupCartRecyclerView() {
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.itemAnimator = DefaultItemAnimator()
        rvCart.adapter = cartAdapter
    }

    private fun registerObservers(){
        viewModel.getCartList().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        response.data.let { cartList ->
                            if (cartList != null) {
                                cartAdapter.cartList.addAll(cartList)
                                cartAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        viewModel.getOriginalValueList().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        response.data.let { totalPrice ->
                            if (totalPrice != null) {
                                tvSumOriginalPrice?.text =
                                    getString(R.string.discount_price_placeholder, totalPrice.toString())
                            }
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })
        viewModel.getDiscountValueList().observe(this, Observer {
            it.let{ response ->
                when (response.status){
                    Status.SUCCESS -> {
                        response.data.let { totalDiscountedPrice ->
                            if (totalDiscountedPrice != null) {
                                tvSumDiscountedPrice?.text =
                                    getString(R.string.discount_price_placeholder, totalDiscountedPrice.toString())
                            }
                        }
                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })

    }
}