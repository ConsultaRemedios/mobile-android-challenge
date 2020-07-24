package java.games.ecommerce.main.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.network.GameService
import java.games.ecommerce.main.network.ResultWrapper
import java.games.ecommerce.main.network.safeApiCall
import javax.inject.Inject

interface GameRepository {
    suspend fun getGames(): ResultWrapper<List<Game>>
    suspend fun getBanners():ResultWrapper<List<Banner>>
    suspend fun searchGame(term: String):ResultWrapper<List<Game>>
}

class GameRepositoryImpl @Inject constructor(
    private var gameService: GameService,
    private var dispatcher: CoroutineDispatcher
) : GameRepository {
    override suspend fun getGames(): ResultWrapper<List<Game>> {
        return safeApiCall(dispatcher) { gameService.getGames() }
    }

    override suspend fun getBanners(): ResultWrapper<List<Banner>> {
        return safeApiCall(dispatcher) { gameService.getBanners() }
    }

    override suspend fun searchGame(term: String): ResultWrapper<List<Game>> {
        return safeApiCall(dispatcher) { gameService.searchGame(term) }
    }
}