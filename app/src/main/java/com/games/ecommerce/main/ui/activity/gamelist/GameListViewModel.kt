package com.games.ecommerce.main.ui.activity.gamelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.games.ecommerce.main.data.model.Banner
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.repository.GameRepository
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.main.network.ResultWrapper
import com.games.ecommerce.utils.SingleLiveEvent
import com.games.ecommerce.utils.asMutable
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameListViewModel @Inject constructor(
    private var repository: GameRepository,
    private var shoppingRepository: ShoppingRepository
) : ViewModel() {
    val games: LiveData<List<GameRepositoryResponse>> = MutableLiveData()
    val gameServiceResponse: SingleLiveEvent<GameRepositoryResponse> = SingleLiveEvent()
    val banners: LiveData<List<Banner>> = MutableLiveData()
    val gamesFound: LiveData<List<GameRepositoryResponse>> = MutableLiveData()
    val cartAmount: LiveData<Int> = MutableLiveData()
    val isSearchTextVisible: LiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<String> = MutableLiveData()

    fun fetchData() {
        viewModelScope.launch {
            cartAmount.asMutable.postValue(shoppingRepository.getTotalAmount())

            when (val response = repository.getGames()) {
                is ResultWrapper.Success -> games.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> {
                    error.asMutable.postValue(ERROR_ON_FIND_GAMES)
                }

                is ResultWrapper.NetworkError -> {
                    error.asMutable.postValue(ERROR_ON_FIND_GAMES)
                }
            }
            when (val response = repository.getBanners()) {
                is ResultWrapper.Success -> banners.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> error.asMutable.postValue(ERROR_ON_FIND_GAMES)

                is ResultWrapper.NetworkError -> error.asMutable.postValue(ERROR_ON_FIND_GAMES)
            }
        }
    }

    fun searchGame(searchTerm: String) {
        viewModelScope.launch {
            when (val response = repository.searchGame(searchTerm)) {
                is ResultWrapper.Success -> gamesFound.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> error.asMutable.postValue(ERROR_ON_FIND_GAMES)


                is ResultWrapper.NetworkError -> error.asMutable.postValue(ERROR_ON_FIND_GAMES)

            }
        }
    }

    fun gameById(id: Int) {
        viewModelScope.launch {
            when (val response = repository.gameById(id)) {
                is ResultWrapper.Success -> gameServiceResponse.asMutable.postValue(response.value)

                is ResultWrapper.GenericError -> error.asMutable.postValue(GENERIC_ERROR)

                is ResultWrapper.NetworkError -> error.asMutable.postValue(GENERIC_ERROR)
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