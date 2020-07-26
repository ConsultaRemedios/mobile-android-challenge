package com.games.ecommerce.main.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.games.ecommerce.main.data.db.dao.ShoppingGameDao

@Database(entities = [ShoppingGameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoppingGameDao(): ShoppingGameDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}