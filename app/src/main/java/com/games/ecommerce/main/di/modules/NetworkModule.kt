package com.games.ecommerce.main.di.modules

import com.games.ecommerce.main.network.GameService
import com.games.ecommerce.utils.Network
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun providesService(): GameService {
        return Network().retrofit().create(GameService::class.java)
    }
}