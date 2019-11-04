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
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.SchedulerProvider
import com.example.mobile_android_challenge.model.ItemCart
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GameViewModel
@Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {

    private val _data = MutableLiveData<GameItem>()
    val data: LiveData<GameItem> = _data

    private val _sizeCart = MutableLiveData<Int>()
    val sizeCart: LiveData<Int> = _sizeCart

    private val _msgLimitItem = MutableLiveData<String>()
    val msgLimitItem: LiveData<String>? = _msgLimitItem


    private val disposable = CompositeDisposable()

    fun fetchGameItem(id: Long) {
        disposable.add(
            api.gameItem(id, NewsApi.API_KEY).subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({
                    _data.value = it
                }, {
                    _data.value = null
                    Log.e("ERROR", it.message)
                }))
    }

    fun addItemCart(context: Context, item: GameItem) {
        var quantity = 1
        var repository = CartRepository(context)

        val itemCart = repository.getItemsCart(item)

        if(itemCart.size == 0){
            repository.addGame(toItemCart(item, quantity))
        }else if (itemCart.first().quantity >= 10) {
            loadCountCart(context, true)
        }else {
            var quantityForUpdate = itemCart.first().quantity + quantity
            repository.updateCartItem(toItemCart(item, quantityForUpdate))
        }
        loadCountCart(context, false)
    }

    private fun toItemCart(item: GameItem, quantity: Int): ItemCart {
        var itemCart: ItemCart
        itemCart = ItemCart(item.id, item.name, item.price, item.platform, item.image, quantity)
        return itemCart
    }

    fun loadCountCart(context: Context, isMaxItem: Boolean) {
        var repository = CartRepository(context)
        if(!isMaxItem) {
            _sizeCart.value = repository.countCart()
        } else {
            _msgLimitItem.value = context.getString(R.string.limit_itens)
        }
    }
}
