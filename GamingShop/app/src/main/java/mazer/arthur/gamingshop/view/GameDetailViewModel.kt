package mazer.arthur.gamingshop.view

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.data.IntentConstants
import mazer.arthur.gamingshop.data.Response

class GameDetailViewModel(private val gamesRepository: GamesRepository): ViewModel()  {

    private var id: Int = 0

    var extras: Bundle? = null
        set(value) {
            field = value
            id = extras?.getInt(IntentConstants.EXTRA_ID_GAME, 0) ?: 0
        }

    fun getGameDetail() = liveData(Dispatchers.IO){
        emit(Response.loading())
        try{
            emit(Response.success(gamesRepository.getGameDetails(id)))
        } catch (ex: Exception){
            emit(Response.error(null, "Error fetching banner list"))
        }
    }
}