package java.games.ecommerce.main.ui.activity.gamedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.data.model.ShoppingGame
import java.games.ecommerce.main.data.model.toShoppingGame
import java.games.ecommerce.main.data.repository.ShoppingRepository
import java.games.ecommerce.utils.asMutable
import javax.inject.Inject

class GameDetailViewModel @Inject constructor(
    private var shoppingRepository: ShoppingRepository
) : ViewModel() {
    val games: LiveData<List<Game>> = MutableLiveData()
    val isOnCart: LiveData<Boolean> = MutableLiveData()


    fun checkGameStatus(game: Game) {
        viewModelScope.launch {
            val shoppingGame = shoppingRepository.getShoppingById(game.id)
            if (shoppingGame != null) {
                isOnCart.asMutable.postValue(true)
                return@launch
            }
            isOnCart.asMutable.postValue(false)
        }
    }

    fun addOrRemoveOfCart(game: Game) {
        viewModelScope.launch {
            var shoppingGame = game.toShoppingGame()
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