package com.benhurqs.checkout.data

import com.benhurqs.network.entities.Cart
import com.benhurqs.network.entities.Spotlight

class CartRepository {

    companion object{
        private const val FREIGHT_FREE = 0.0

        private var INSTANCE: CartRepository? = null
        private var cartList: HashMap<Int, Cart> = HashMap()
        var freight: Double = FREIGHT_FREE
        var total: Double = 0.0

        fun getInstance(): CartRepository{
            if(INSTANCE == null){
                INSTANCE = CartRepository()
            }

            return INSTANCE!!
        }
    }

    fun hasItem(id: Int): Boolean = cartList.containsKey(id)

    fun addItem(item: Spotlight){
        if(hasItem(item.id)){
            cartList[item.id]!!.qtd++
        }else{
            cartList[item.id] = Cart.convertToCart(item)
        }

        updateValue()
    }

    fun removeItem(item: Spotlight){
        if(hasItem(item.id)){
            cartList.remove(item.id)
            updateValue()
        }
    }

    fun getTotalItem() = cartList.size

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
    }

    fun getList(): List<Cart>{
        return cartList.values.toList()
    }



}