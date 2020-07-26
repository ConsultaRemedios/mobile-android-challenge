package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.Provides
import java.games.ecommerce.main.network.GameService
import java.games.ecommerce.utils.Network

@Module
class NetworkModule {
    @Provides
    fun providesService() : GameService {
        return Network.retrofit.create(GameService::class.java)
    }
}