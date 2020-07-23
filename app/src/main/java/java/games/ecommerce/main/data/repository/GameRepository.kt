package java.games.ecommerce.main.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.data.service.GameService
import javax.inject.Inject

interface GameRepository {
    suspend fun getGames(): List<Game>
    suspend fun getBanners(): List<Banner>
}

class GameRepositoryImpl @Inject constructor(
    private var gameService: GameService
) : GameRepository {
    override suspend fun getGames(): List<Game> {
        return gameService.getGames()
    }

    override suspend fun getBanners(): List<Banner> {
        return gameService.getBanners()
    }
}