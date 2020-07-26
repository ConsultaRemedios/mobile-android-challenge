package java.games.ecommerce.main.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import java.games.ecommerce.main.data.db.AppDatabase
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.data.repository.ShoppingRepository
import java.games.ecommerce.main.data.repository.ShoppingRepositoryImpl
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(mApplication: Application): AppDatabase {
        return Room.databaseBuilder<AppDatabase>(mApplication, AppDatabase::class.java, "shopping-cart-db")
                .build()
    }

    @Singleton
    @Provides
    fun providesShoppingGameDao(appDatabase: AppDatabase): ShoppingGameDao {
        return appDatabase.shoppingGameDao()
    }

    @Singleton
    @Provides
    fun providesRepository(shoppingGameDao: ShoppingGameDao): ShoppingRepository {
        return ShoppingRepositoryImpl(shoppingGameDao)
    }
}