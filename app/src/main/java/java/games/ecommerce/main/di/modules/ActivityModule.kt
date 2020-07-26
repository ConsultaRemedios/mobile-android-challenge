package java.games.ecommerce.main.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import java.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import java.games.ecommerce.main.ui.activity.gamelist.ListActivity
import java.games.ecommerce.main.ui.activity.shoppingcart.ShoppingCartActivity
import java.games.ecommerce.main.ui.fragment.searchgame.SearchGameFragment

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeGamesListActivity() : ListActivity

    @ContributesAndroidInjector
    abstract fun contributeGameDetailActivity() : GameDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchGameFragment() : SearchGameFragment

    @ContributesAndroidInjector
    abstract fun contributeShoppingCartActivity() : ShoppingCartActivity
}