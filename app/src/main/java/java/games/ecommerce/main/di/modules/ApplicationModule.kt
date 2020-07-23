package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.Provides
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.data.repository.GameRepositoryImpl
import java.games.ecommerce.main.data.service.GameService

@Module
class ApplicationModule {

    @Provides
    fun provideRepository(gameService: GameService) : GameRepository {
        return GameRepositoryImpl(gameService)
    }
}