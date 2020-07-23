package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.Provides
import java.games.ecommerce.main.data.service.GameService
import java.games.ecommerce.utils.Network

@Module
class NetworkModule {
    @Provides
    fun provideService() : GameService {
        return Network.retrofit.create(GameService::class.java)
    }
}