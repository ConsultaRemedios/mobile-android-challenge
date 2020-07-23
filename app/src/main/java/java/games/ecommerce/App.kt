package java.games.ecommerce

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.games.ecommerce.main.di.DaggerApplicationComponent

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