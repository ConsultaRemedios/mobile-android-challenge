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
import mazer.arthur.gamingshop.utils.listeners.CartAdapterListener
import mazer.arthur.gamingshop.view.adapter.CartAdapter

class CartActivity : AppCompatActivity(), CartAdapterListener {

    private lateinit var viewModel: CartViewModel
    private var cartAdapter = CartAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupViewModel()
        setupCartRecyclerView()
        registerObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.calculateShippingValue()
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
        viewModel.getOriginalValueList.observe(this, Observer {
            if (it != null){
                tvSumOriginalPrice?.text =
                    getString(R.string.price_placeholder, it.toString())
            }else{
                tvSumOriginalPrice?.text =
                    getString(R.string.no_items_cart_price)
            }
        })
        viewModel.getDiscountValueList.observe(this, Observer {
            if (it != null){
                tvSumDiscountedPrice?.text =
                    getString(R.string.price_placeholder, it.toString())
            }else{
                tvSumDiscountedPrice?.text =
                    getString(R.string.no_items_cart_price)
            }
        })
        viewModel.getTotalItemsCart.observe(this, Observer {
            if (it != null){
                tvQntProducts?.text =
                    getString(R.string.products_label, it.toString())
            }else{
                tvQntProducts?.text =
                    getString(R.string.products_label, "0")
            }
        })
        viewModel.eventLiveData.observe(this, Observer {
            when(it){
                is CartViewModel.ViewEvent.ShippingValueChanged -> {
                   if (it.quant > 0){
                       tvShippingPrice?.text = getString(R.string.price_placeholder, it.quant.toString())
                   }else{
                       tvShippingPrice?.text = getString(R.string.free_shipping)
                   }
                }
                is CartViewModel.ViewEvent.EmptyCart -> {
                    tvShippingPrice?.text = getString(R.string.shipping_no_items)
                }
            }
        })

    }

    override fun onDeleteClicked(id: Int) {
        viewModel.removeItemCart(id)
    }
}