package java.games.ecommerce.main.ui.activity.gamelist

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
    val gamesFound: LiveData<List<Game>> = MutableLiveData()
    val isSearchTextVisible: LiveData<Boolean> = MutableLiveData(false)

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

    fun searchGame(searchTerm: String) {
        viewModelScope.launch {
            when(val response = repository.searchGame(searchTerm)) {
                is ResultWrapper.Success -> gamesFound.asMutable.postValue(response.value)
            }
        }
    }
    fun checkFragmentVisibility(length: Int){
        if(length < 1 && isSearchTextVisible.value!!) {
            isSearchTextVisible.asMutable.postValue(false)
            return
        }
        if (length >= 1 && !isSearchTextVisible.value!!) {
            isSearchTextVisible.asMutable.postValue(true)
        }

    }

}