package java.games.ecommerce.main.ui.activity.gamelist

import android.R
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.data.model.ShoppingGame
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.data.repository.ShoppingRepository
import java.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import java.games.ecommerce.main.network.ResultWrapper
import java.games.ecommerce.utils.asMutable
import java.math.BigDecimal
import javax.inject.Inject

class GameListViewModel @Inject constructor(
    private var repository: GameRepository,
    private var shoppingRepository: ShoppingRepository
) : ViewModel() {
    val games: LiveData<List<Game>> = MutableLiveData()
    val game: LiveData<Game> = MutableLiveData()
    val banners: LiveData<List<Banner>> = MutableLiveData()
    val gamesFound: LiveData<List<Game>> = MutableLiveData()
    val cartAmount: LiveData<Int> = MutableLiveData()
    val isSearchTextVisible: LiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<String> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch {
            cartAmount.asMutable.postValue(shoppingRepository.getTotalAmount())
            
            when (val response = repository.getGames()) {
                is ResultWrapper.Success -> games.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }

                is ResultWrapper.NetworkError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }
            }
            when (val response = repository.getBanners()) {
                is ResultWrapper.Success -> banners.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }

                is ResultWrapper.NetworkError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }
            }
        }
    }

    fun searchGame(searchTerm: String) {
        viewModelScope.launch {
            when (val response = repository.searchGame(searchTerm)) {
                is ResultWrapper.Success -> gamesFound.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }

                is ResultWrapper.NetworkError -> {
                    error.asMutable.postValue("Ocorreu um erro ao buscar os jogos, tente novamente")
                    return@launch
                }
            }
        }
    }

    fun gameById(id: Int) {
        viewModelScope.launch {
            when (val response = repository.gameById(id)) {
                is ResultWrapper.Success -> game.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> error.asMutable.postValue("Ocorreu um erro, tente novamente")

                is ResultWrapper.NetworkError -> error.asMutable.postValue("Ocorreu um erro, tente novamente")
            }
        }
    }

    fun checkFragmentVisibility(length: Int) {
        if (length < 1 && isSearchTextVisible.value!!) {
            isSearchTextVisible.asMutable.postValue(false)
            return
        }
        if (length >= 1 && !isSearchTextVisible.value!!) {
            isSearchTextVisible.asMutable.postValue(true)
        }
    }
}