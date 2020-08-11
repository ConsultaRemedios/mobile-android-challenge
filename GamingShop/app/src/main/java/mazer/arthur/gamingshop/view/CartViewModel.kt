package mazer.arthur.gamingshop.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.data.remote.Response

class CartViewModel(private val gamesRepository: GamesRepository): ViewModel()   {

    fun getCartList() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getCartList()))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching cart list"))
        }
    }

    fun getOriginalValueList() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getTotalOriginalSumCart()))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching total price cart list"))
        }
    }

    fun getDiscountValueList() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getTotalDiscountSumCart()))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching total price cart list"))
        }
    }


}