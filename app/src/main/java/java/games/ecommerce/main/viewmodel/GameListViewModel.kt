package java.games.ecommerce.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.games.ecommerce.main.data.repository.GameRepository
import javax.inject.Inject

class GameListViewModel @Inject constructor(
    private var repository: GameRepository
) : ViewModel() {
    val games = liveData(Dispatchers.IO) {
        val result = repository.getGames()
        emit(result)
    }
    val banners = liveData(Dispatchers.IO) {
        val result = repository.getBanners()
        emit(result)
    }
}