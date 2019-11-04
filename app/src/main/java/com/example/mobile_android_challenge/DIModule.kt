package com.example.mobile_android_challenge

import android.content.Context
import com.example.cheesecakenews.MyApp
import com.example.cheesecakenews.api.NetworkModule
import com.example.mobile_android_challenge.view.cart.CartActivity
import com.example.mobile_android_challenge.view.checkout.CheckoutActivity
import com.example.mobile_android_challenge.view.game.GameActivity
import com.example.mobile_android_challenge.view.games_list.GamesActivity

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module(includes = [
    NetworkModule::class,
    SchedulerModule::class
])

class AppModule

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun gamesActivity(): GamesActivity

    @ContributesAndroidInjector
    abstract fun gameActivity(): GameActivity

    @ContributesAndroidInjector
    abstract fun cartActivity(): CartActivity

    @ContributesAndroidInjector
    abstract fun checkoutActivity(): CheckoutActivity
}

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    AppModule::class,
    AndroidInjectorsModule::class
))

interface AppComponent : AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApp>() {
        @BindsInstance
        abstract fun appContext(appContext: Context): Builder
    }
}