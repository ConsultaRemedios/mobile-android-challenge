package com.benhurqs.checkout.data

import com.benhurqs.network.entities.Cart
import com.benhurqs.network.entities.Spotlight

class CartRepository {


    var freight: Double = FREIGHT_FREE
    var total: Double = 0.0

    companion object{
        const val FREIGHT_FREE = 0.0

        private var INSTANCE: CartRepository? = null
        private var cartList: HashMap<Int, Cart> = HashMap()
        private var observerList = ArrayList<CartUpdateListener>()


        fun getInstance(): CartRepository{
            if(INSTANCE == null){
                INSTANCE = CartRepository()
            }

            return INSTANCE!!
        }
    }

    fun hasItem(id: Int): Boolean = cartList.containsKey(id)

    fun addItem(item: Spotlight): Int{
        if(hasItem(item.id)){
            cartList[item.id]!!.qtd++
        }else{
            cartList[item.id] = Cart.convertToCart(item)
        }

        updateValue()
        return cartList[item.id]!!.qtd
    }

    fun removeQtdItem(item: Spotlight): Int?{
        if(hasItem(item.id)){
            val cartItem = cartList[item.id]
            if(cartItem!!.qtd > 0) {
                cartItem.qtd--
            }else{
                removeItem(item)
            }
        }else{
            cartList[item.id] = Cart.convertToCart(item)
        }

        updateValue()
        return cartList[item.id]?.qtd
    }

    fun removeItem(item: Spotlight){
        if(hasItem(item.id)){
            cartList.remove(item.id)
            updateValue()
        }
    }


    private fun updateValue(){
        total = 0.0
        freight = 0.0
        cartList.forEach {
            total += it.value.qtd * (it.value.price - it.value.discount)
            freight += 10.0
        }

        if(total > 250.0){
            freight = FREIGHT_FREE
        }

        notifyObservers()
    }

    fun addObserver(listener: CartUpdateListener){
        observerList.add(listener)
    }

    fun removeObserver(listener: CartUpdateListener){
        observerList.remove(listener)
    }

    fun clearCart(){
        cartList.clear()
    }

    private fun notifyObservers(){
        observerList.forEach {
            it.onUpdate()
        }
    }

    fun getList(): ArrayList<Cart>{
        return ArrayList(cartList.values.toList())
    }



}