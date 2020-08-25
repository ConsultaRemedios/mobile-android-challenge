package com.games.ecommerce.main.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.games.ecommerce.main.data.repository.GameRepository
import com.games.ecommerce.main.data.repository.ShoppingRepository
import com.games.ecommerce.main.ui.activity.gamedetails.GameDetailViewModel
import com.games.ecommerce.main.ui.activity.gamelist.GameListViewModel
import com.games.ecommerce.main.ui.activity.shoppingcart.ShoppingCartViewModel
import com.games.ecommerce.utils.ViewModelFactory
import com.games.ecommerce.utils.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
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