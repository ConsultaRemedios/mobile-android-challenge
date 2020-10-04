package br.com.challenge.consultaremedios.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.challenge.consultaremedios.db.AppDatabase
import br.com.challenge.consultaremedios.db.dao.CartDao
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.db.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val cartDao: CartDao = AppDatabase.getAppAdapter(application)!!.cartDao()
    private val cartRepository = CartRepository(application)
    val cartItems: LiveData<List<CartItem>>

    init {
        cartItems = cartDao.getAll()
    }

    fun insert(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.insert(cartItem)
    }

    fun update(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.update(cartItem)
    }

    fun delete(cartItem: CartItem) = viewModelScope.launch {
        cartRepository.delete(cartItem)
    }
}