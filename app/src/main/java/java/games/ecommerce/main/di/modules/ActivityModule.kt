package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import java.games.ecommerce.main.ui.activity.gamelist.ListActivity
import java.games.ecommerce.main.ui.fragment.searchgame.SearchGameFragment

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeGamesListActivity() : ListActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchGameFragment() : SearchGameFragment
}