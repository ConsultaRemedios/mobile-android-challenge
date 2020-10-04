package br.com.challenge.consultaremedios.db.repository

import android.app.Application
import br.com.challenge.consultaremedios.db.AppDatabase
import br.com.challenge.consultaremedios.db.dao.CartDao
import br.com.challenge.consultaremedios.db.entity.CartItem

class CartRepository(application: Application) {

    private val cartDao: CartDao = AppDatabase.getAppAdapter(application)!!.cartDao()

    suspend fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun update(cartItem: CartItem) {
        cartDao.update(cartItem)
    }

    suspend fun delete(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

}