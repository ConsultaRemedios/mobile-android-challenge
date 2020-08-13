package mazer.arthur.gamingshop.view

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.data.remote.Response
import mazer.arthur.gamingshop.domain.usecases.AddOrRemoveItemCartUseCase
import mazer.arthur.gamingshop.domain.usecases.GetQuantItemCartUseCase
import mazer.arthur.gamingshop.utils.listeners.CartQuantItemsListener

class MainViewModel(private val gamesRepository: GamesRepository): ViewModel(),
    CartQuantItemsListener {

    sealed class ViewEvent {
        class NumItemCartChanged(val quant: Int): ViewEvent()
        object EmptyCart: ViewEvent()
    }

    var eventLiveData = MutableLiveData<ViewEvent>()

    fun getBanners() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getBannersList()))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching banner list"))
        }
    }

    fun getSpotlight() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getSpotlightList()))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching spotlight list ${ex.message}"))
        }
    }

    fun getTotalCartItems(){
        val cartQuantItemUseCase = GetQuantItemCartUseCase(gamesRepository, this)
        GlobalScope.launch {
            cartQuantItemUseCase.getNumItems()
        }
    }

    override fun hasItems(quant: Int) {
        eventLiveData.postValue(ViewEvent.NumItemCartChanged(quant))
    }

    override fun emptyCart() {
        eventLiveData.postValue(ViewEvent.EmptyCart)
    }
}