package com.games.ecommerce.main.data.repository

import com.games.ecommerce.main.data.model.Banner
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.GameServiceResponse
import com.games.ecommerce.main.data.model.toGameRepositoryResponse
import com.games.ecommerce.main.network.GameService
import com.games.ecommerce.main.network.ResultWrapper
import com.games.ecommerce.main.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface GameRepository {
    suspend fun getGames(): ResultWrapper<List<GameRepositoryResponse>>
    suspend fun getBanners(): ResultWrapper<List<Banner>>
    suspend fun searchGame(term: String): ResultWrapper<List<GameRepositoryResponse>>
    suspend fun gameById(id: Int): ResultWrapper<GameRepositoryResponse>
    suspend fun checkout(): ResultWrapper<Unit>
}

class GameRepositoryImpl @Inject constructor(
    private var gameService: GameService,
    private var dispatcher: CoroutineDispatcher
) : GameRepository {
    override suspend fun getGames(): ResultWrapper<List<GameRepositoryResponse>> {
        return safeApiCall(dispatcher) {
            gameService.getGames().map {
                it.toGameRepositoryResponse()
            }
        }
    }

    override suspend fun getBanners(): ResultWrapper<List<Banner>> {
        return safeApiCall(dispatcher) { gameService.getBanners() }
    }

    override suspend fun searchGame(term: String): ResultWrapper<List<GameRepositoryResponse>> {
        return safeApiCall(dispatcher) {
            gameService.searchGame(term).map {
                it.toGameRepositoryResponse()
            }
        }
    }

    override suspend fun gameById(id: Int): ResultWrapper<GameRepositoryResponse> {
        return safeApiCall(dispatcher) { gameService.gameById(id).toGameRepositoryResponse() }
    }

    override suspend fun checkout(): ResultWrapper<Unit> {
        return safeApiCall(dispatcher) { gameService.checkout() }
    }
}