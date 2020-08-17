package br.com.weslleymaciel.gamesecommerce.common.utils

import android.content.SharedPreferences
import br.com.weslleymaciel.gamesecommerce.MainActivity
import br.com.weslleymaciel.gamesecommerce.common.models.CartItem
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object CartHelper {
    val PREFS_FILENAME = "cbr.com.weslleymaciel.gamesecommerce.prefs"
    val CART_COUNTER = "cart_counter"
    val CART_LIST = "cart_list"

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    init {
        sharedPreferences = MainActivity.ctx!!.getSharedPreferences(PREFS_FILENAME, 0)
        editor = sharedPreferences!!.edit()
    }

    private fun setCartCounter(){
        val cartItems = getCartList()
        var itemCounter = 0

        for (cartItem in cartItems) {
            itemCounter += cartItem.count.toInt()
        }

        editor!!.putInt(CART_COUNTER, itemCounter)
        editor!!.apply()
    }

    fun getCartCounter(): Int{
        return  sharedPreferences!!.getInt(CART_COUNTER, 0)
    }

    fun setCartList(cartItems: MutableList<CartItem>){
        var gson = Gson()
        var jsonCartList = gson.toJson(cartItems)
        editor!!.putString(CART_LIST, jsonCartList)
        editor!!.apply()
        setCartCounter()
    }

    fun getCartList(): MutableList<CartItem>{
        var gson = Gson()
        val listType: Type = object : TypeToken<MutableList<CartItem?>?>() {}.type
        return  gson.fromJson(sharedPreferences!!.getString(CART_LIST, "[]"), listType)
    }

    fun isItemOnCart(gameId: Int): Boolean{
        val cartItems = getCartList()

        if (cartItems.isNotEmpty()){
            for (cartItem in cartItems) {
                if (cartItem.game_id.toInt() == gameId){
                    return true
                }
            }
        }

        return false
    }

    fun addCount(gameId: Int): Int{
        val cartItems = getCartList()

        for (i in 0 until cartItems.size) {
            if (cartItems[i].game_id.toInt() == gameId){
                cartItems[i].count = cartItems[i].count.toInt() + 1
                setCartList(cartItems)
                setCartCounter()
                return cartItems[i].count.toInt()

            }
        }

        return 0
    }

    fun removeCount(gameId: Int): Int{
        val cartItems = getCartList()

        for (i in 0 until cartItems.size) {
            if (cartItems[i].game_id.toInt() == gameId){
                cartItems[i].count = cartItems[i].count.toInt() - 1
                if (cartItems[i].count.toInt() <= 0) {
                    removeItemFromCart(gameId)
                }else{
                    setCartList(cartItems)
                    setCartCounter()
                }
                return cartItems[i].count.toInt()
            }
        }
        return 0
    }

    fun addItemToCart(game: Game){
        val cartItems = getCartList()
        var cartItem = CartItem(game.id,game.id,game.image,game.title,1,game.discount,game.price)

        cartItems.add(cartItem)
        setCartList(cartItems)
        setCartCounter()
    }

    fun removeItemFromCart(gameId: Int){
        val cartItems = getCartList()

        for (cartItem in cartItems) {
            if (cartItem.game_id.toInt() == gameId){
                cartItems.remove(cartItem)
                break
            }
        }

        setCartList(cartItems)
        setCartCounter()
    }

    fun removeAll(){
        setCartList(mutableListOf())
        setCartCounter()
    }
}