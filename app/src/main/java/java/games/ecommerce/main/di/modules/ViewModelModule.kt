package java.games.ecommerce.main.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.data.repository.ShoppingRepository
import java.games.ecommerce.main.ui.activity.gamedetails.GameDetailViewModel
import java.games.ecommerce.main.ui.activity.gamelist.GameListViewModel
import java.games.ecommerce.main.ui.activity.shoppingcart.ShoppingCartViewModel
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.ViewModelKey
import javax.inject.Provider

@Module
class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(GameListViewModel::class)
    fun providesListGameViewModule(
        gameRepository: GameRepository,
        shoppingRepository: ShoppingRepository
    ): ViewModel {
        return GameListViewModel(
            gameRepository, shoppingRepository
        )
    }

    @Provides
    @IntoMap
    @ViewModelKey(GameDetailViewModel::class)
    fun providesGameDetailViewModule(shoppingRepository: ShoppingRepository): ViewModel {
        return GameDetailViewModel(shoppingRepository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ShoppingCartViewModel::class)
    fun providesShoppingCartViewModule(
        gameRepository: GameRepository,
        shoppingRepository: ShoppingRepository
    ): ViewModel {
        return ShoppingCartViewModel(shoppingRepository, gameRepository)
    }

    @Provides
    fun providesViewModelFactory(
        map: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(map)
    }

}