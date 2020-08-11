package mazer.arthur.gamingshop.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mazer.arthur.gamingshop.data.remote.ApiHelper
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.view.CartViewModel
import mazer.arthur.gamingshop.view.GameDetailViewModel
import mazer.arthur.gamingshop.view.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val context: Context) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                GamesRepository(
                    apiHelper,
                    context
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(GameDetailViewModel::class.java)) {
            return GameDetailViewModel(
                GamesRepository(
                    apiHelper,
                    context
                )
            ) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(
                GamesRepository(
                    apiHelper,
                    context
                )
            ) as T
        }
        throw IllegalArgumentException("Class name not found")
    }

}