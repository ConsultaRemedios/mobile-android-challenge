package mazer.arthur.gamingshop.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.data.Response

class MainViewModel(private val gamesRepository: GamesRepository): ViewModel() {

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
}