package com.games.ecommerce.main.ui.activity.gamedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.toShoppingGame
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.utils.asMutable
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameDetailViewModel @Inject constructor(
    private var shoppingRepository: ShoppingRepository
) : ViewModel() {
    val isOnCart: LiveData<Boolean> = MutableLiveData()

    fun checkGameStatus(gameRepositoryResponse: GameRepositoryResponse) {
        viewModelScope.launch {
            val shoppingGame = shoppingRepository.getShoppingById(gameRepositoryResponse.id)
            if (shoppingGame != null) {
                isOnCart.asMutable.postValue(true)
                return@launch
            }
            isOnCart.asMutable.postValue(false)
        }
    }

    fun addOrRemoveOfCart(gameRepositoryResponse: GameRepositoryResponse) {
        viewModelScope.launch {
            var shoppingGame = gameRepositoryResponse.toShoppingGame()
            val shoppingOnDB = shoppingRepository.getShoppingById(shoppingGame.id)
            if (shoppingOnDB == null) {
                shoppingGame.amount = 1
                shoppingRepository.saveShoppingGame(shoppingGame)
                isOnCart.asMutable.postValue(true)
                return@launch
            }
            shoppingRepository.removeShoppingGame(shoppingGame.id)
            isOnCart.asMutable.postValue(false)
        }
    }
}