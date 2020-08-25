package com.games.ecommerce.main.di.modules

import com.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import com.games.ecommerce.main.ui.activity.gamelist.ListActivity
import com.games.ecommerce.main.ui.activity.shoppingcart.ShoppingCartActivity
import com.games.ecommerce.main.ui.fragment.searchgame.SearchGameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeGamesListActivity(): ListActivity

    @ContributesAndroidInjector
    abstract fun contributeGameDetailActivity(): GameDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchGameFragment(): SearchGameFragment

    @ContributesAndroidInjector
    abstract fun contributeShoppingCartActivity(): ShoppingCartActivity
}