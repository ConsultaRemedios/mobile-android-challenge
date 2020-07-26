package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.data.repository.GameRepositoryImpl
import java.games.ecommerce.main.data.repository.ShoppingRepository
import java.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import java.games.ecommerce.main.network.GameService

@Module
class ApplicationModule {

    @Provides
    fun providesRepository(gameService: GameService) : GameRepository {
        return GameRepositoryImpl(gameService, Dispatchers.IO)
    }
}