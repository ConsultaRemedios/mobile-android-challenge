package mazer.arthur.gamingshop.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mazer.arthur.gamingshop.view.GameDetailViewModel
import mazer.arthur.gamingshop.view.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(GamesRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(GameDetailViewModel::class.java)) {
            return GameDetailViewModel(GamesRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Class name not found")
    }

}