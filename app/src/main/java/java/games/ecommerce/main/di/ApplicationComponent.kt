package java.games.ecommerce.main.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import java.games.ecommerce.App
import java.games.ecommerce.main.data.db.AppDatabase
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.di.modules.*
import javax.inject.Singleton


@Singleton
@Component(
    modules = ([
        ActivityModule::class,
        AndroidInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ])
)

interface ApplicationComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}