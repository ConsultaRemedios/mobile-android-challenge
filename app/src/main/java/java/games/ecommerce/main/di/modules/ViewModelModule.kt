package java.games.ecommerce.main.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.games.ecommerce.main.data.repository.GameRepository
import java.games.ecommerce.main.viewmodel.GameListViewModel
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.ViewModelKey
import javax.inject.Provider

@Module
class ViewModelModule {
    @Provides
    @IntoMap
    @ViewModelKey(GameListViewModel::class)
    fun provideMovieViewModule(movieRepository: GameRepository) : ViewModel {
        return GameListViewModel(movieRepository)
    }

    @Provides
    fun provideViewModelFactory(map: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {
        return ViewModelFactory(map)
    }

}