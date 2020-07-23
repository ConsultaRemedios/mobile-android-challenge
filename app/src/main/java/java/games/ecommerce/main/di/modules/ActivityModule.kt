package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import java.games.ecommerce.main.view.ListActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMoviesListActivity() : ListActivity
}