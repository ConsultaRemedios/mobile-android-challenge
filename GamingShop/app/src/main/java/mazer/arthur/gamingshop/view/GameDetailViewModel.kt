package mazer.arthur.gamingshop.view

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.IntentConstants
import mazer.arthur.gamingshop.data.remote.Response
import mazer.arthur.gamingshop.data.remote.entities.Cart
import mazer.arthur.gamingshop.domain.models.GameDetails
import mazer.arthur.gamingshop.domain.usecases.AddOrRemoveItemCartUseCase
import mazer.arthur.gamingshop.utils.listeners.CartChangedListener

class GameDetailViewModel(private val gamesRepository: GamesRepository): ViewModel(), CartChangedListener  {

    sealed class ViewEvent {
        object ItemAdded: ViewEvent()
        object ItemRemoved: ViewEvent()
        object Error: ViewEvent()
    }

    var eventLiveData = MutableLiveData<ViewEvent>()

    private lateinit var gameDetails: GameDetails
    var extras: Bundle? = null
        set(value) {
            field = value
            gameDetails = extras?.getParcelable(IntentConstants.EXTRA_GAME_DETAILS) ?: return
        }

    fun getGameDetail() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getGameDetails(gameDetails?.idGameDetails)))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching banner list"))
        }
    }

    fun getItemCart() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.isItemOnCart(gameDetails?.idGameDetails)))
        } catch (ex: Exception){
            emit(Response.error(null, "Error get item cart"))
        }
    }


    fun onCartButtonClicked(){
        val cart = Cart(null, gameDetails.idGameDetails, 1, gameDetails.title, gameDetails.price, gameDetails.discount, gameDetails.image)
        val cartChangedUseCase = AddOrRemoveItemCartUseCase(gamesRepository, this)
        GlobalScope.launch {
            cartChangedUseCase.addOrRemove(cart)
        }
    }

    /** listeners use cases **/
    override fun itemAdded() {
        eventLiveData.postValue(ViewEvent.ItemAdded)
    }

    override fun itemRemoved() {
        eventLiveData.postValue(ViewEvent.ItemRemoved)
    }

    override fun error() {
        eventLiveData.postValue(ViewEvent.Error)
    }
}