package com.example.cheesecakecart.view.games

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cheesecakenews.data.DBHelper
import com.example.cheesecakenews.model.GameItem
import com.example.mobile_android_challenge.model.ItemCart
import io.reactivex.disposables.CompositeDisposable

class CartRepository(context: Context) {
    var cartDBHelper : DBHelper
    var listGames: MutableList<ItemCart> = mutableListOf()

    private val _data = MutableLiveData<List<ItemCart>>()
    val data: LiveData<List<ItemCart>> = _data
    private val disposable = CompositeDisposable()

    init {
        cartDBHelper = DBHelper(context.applicationContext)
    }

    fun getCartItems(): ArrayList<ItemCart> {
        return cartDBHelper.readAllItemsCart()
    }

    fun addGame(itemCart: ItemCart){
        cartDBHelper.insertCart(itemCart)
    }

    fun getItemsCart(game: GameItem): ArrayList<ItemCart> {
        return cartDBHelper.readItemCart(game.id)
    }

    fun updateCartItem(game: ItemCart): Boolean {
        return cartDBHelper.updateCartItem(game)
    }

    fun countCart(): Int {
        return cartDBHelper.readItemsCart().map { it.quantity}.sum()
    }
}