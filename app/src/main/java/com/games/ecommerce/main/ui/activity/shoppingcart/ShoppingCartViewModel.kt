package com.games.ecommerce.main.ui.activity.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.ecommerce.main.data.model.ShoppingGame
import com.games.ecommerce.main.data.repository.GameRepository
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.main.data.repository.StatusDB
import com.games.ecommerce.main.network.ResultWrapper
import com.games.ecommerce.utils.asMutable
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingCartViewModel @Inject constructor(
    private var repository: ShoppingRepository,
    private var gameRepository: GameRepository
) : ViewModel() {
    val shoppingGames: LiveData<List<ShoppingGame>> = MutableLiveData()
    val statusDB: LiveData<StatusDB> = MutableLiveData()
    val checkoutSuccess: LiveData<Boolean> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch {
            shoppingGames.asMutable.postValue(repository.getShoppingGames())
        }
    }

    fun checkout() {
        viewModelScope.launch {
            when (val response = gameRepository.checkout()) {
                is ResultWrapper.Success -> checkoutSuccess.asMutable.postValue(true)

                is ResultWrapper.GenericError -> {
                    checkoutSuccess.asMutable.postValue(false)
                    return@launch
                }

                is ResultWrapper.NetworkError -> {
                    checkoutSuccess.asMutable.postValue(false)
                    return@launch
                }
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.removeAllShoppingGames()
        }
    }

    private fun getTotalPrice(shoppingGames: List<ShoppingGame>): Double {
        var result = 0.0
        shoppingGames.forEach {
            result += it.price * it.amount
        }
        return result
    }

    private fun deliveryCharge(totalPrice: Double, amount: Int): Double {
        val totalPrice = totalPrice
        if (totalPrice > 250.0) return 0.0
        return amount * 10.0
    }

    fun getPriceWithCharges(shoppingGames: List<ShoppingGame>): Double {
        val totalPrice = getTotalPrice(shoppingGames)
        val amount = getTotalAmount(shoppingGames)
        return deliveryCharge(totalPrice, amount) + totalPrice
    }

    fun getPrice(shoppingGames: List<ShoppingGame>): Double {
        var result = 0.0
        shoppingGames.forEach {
            result += it.price * it.amount
        }
        return result
    }

    fun getTotalAmount(shoppingGames: List<ShoppingGame>): Int {
        var result = 0
        shoppingGames.forEach {
            result += it.amount
        }
        return result
    }

    fun addOnCart(shoppingGame: ShoppingGame) {
        viewModelScope.launch {
            var dbGame = repository.getShoppingById(shoppingGame.id)
            dbGame.amount = dbGame.amount + 1
            statusDB.asMutable.postValue(repository.saveShoppingGame(dbGame))
        }
    }

    fun removeFromCart(shoppingGame: ShoppingGame) {
        viewModelScope.launch {
            var dbGame = repository.getShoppingById(shoppingGame.id)
            if (dbGame.amount == 1) {
                statusDB.asMutable.postValue(repository.removeShoppingGame(dbGame.id))
                return@launch
            }
            dbGame.amount = dbGame.amount - 1
            statusDB.asMutable.postValue(repository.saveShoppingGame(dbGame))
        }
    }

    fun removeAllFromCart(shoppingGame: ShoppingGame) {
        viewModelScope.launch {
            statusDB.asMutable.postValue(repository.removeShoppingGame(shoppingGame.id))
        }
    }
}