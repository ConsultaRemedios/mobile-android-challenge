package java.games.ecommerce.main.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.network.ResultWrapper
import java.games.ecommerce.utils.asMutable
import javax.inject.Inject

class GameListViewModel @Inject constructor(
    private var repository: GameRepository
) : ViewModel() {
    val games: LiveData<List<Game>> = MutableLiveData()
    val banners: LiveData<List<Banner>> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch {
            when(val response = repository.getGames()) {
                is ResultWrapper.Success -> games.asMutable.postValue(response.value)
            }
            when(val response = repository.getBanners()) {
                is ResultWrapper.Success -> banners.asMutable.postValue(response.value)
            }
        }
    }
}