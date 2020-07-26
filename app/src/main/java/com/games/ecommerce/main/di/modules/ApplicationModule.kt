package com.games.ecommerce.main.di.modules

import com.games.ecommerce.main.data.repository.GameRepository
import com.games.ecommerce.main.data.repository.GameRepositoryImpl
import com.games.ecommerce.main.network.GameService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ApplicationModule {

    @Provides
    fun providesRepository(gameService: GameService): GameRepository {
        return GameRepositoryImpl(gameService, Dispatchers.IO)
    }
}