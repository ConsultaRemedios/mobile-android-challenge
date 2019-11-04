package com.example.mobile_android_challenge.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesecakecart.view.games.CartRepository
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.api.NewsApi
import com.example.cheesecakenews.model.GameItem
import com.example.mobile_android_challenge.SchedulerProvider
import com.example.mobile_android_challenge.model.ItemCart
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CartViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    private val _data = MutableLiveData<List<ItemCart>>()
    val data: LiveData<List<ItemCart>> = _data

    private val _sizeCart = MutableLiveData<Int>()
    val sizeCart: LiveData<Int> = _sizeCart

    private val _checkoutCart = MutableLiveData<String>()
    val checkoutCart: LiveData<String> = _checkoutCart

    private val disposable = CompositeDisposable()

    fun fetchCartItems(context: Context) {
        var repository = CartRepository(context)
        _data.value = repository.getCartItems()
        loadCountCart(context)
    }

    fun updateItemCart(context: Context, item: GameItem, quantity: Int) {
        var repository = CartRepository(context)
        repository.updateCartItem(toItemCart(item, quantity))
    }

    private fun toItemCart(item: GameItem, quantity: Int): ItemCart {
        var itemCart: ItemCart
        itemCart = ItemCart(item.id, item.name, item.price, item.platform, item.image, quantity)
        return itemCart
    }

    fun loadCountCart(context: Context) {
        var repository = CartRepository(context)
        _sizeCart.value = repository.countCart()
    }

    fun finishCart() {
        disposable.add(
            api.gameCheckout(NewsApi.API_KEY).subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({
                    _checkoutCart.value = it
                }, {
                    _data.value = null
                    Log.e("ERROR", it.message)
                    _checkoutCart.value = it.message
                }))
    }
}
