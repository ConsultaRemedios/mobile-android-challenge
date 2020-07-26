package com.games.ecommerce

import android.app.Application
import com.games.ecommerce.main.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    private lateinit var applicaton: Application

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)

        return appComponent
    }
}