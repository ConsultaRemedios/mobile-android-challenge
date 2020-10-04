package br.com.challenge.consultaremedios.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.challenge.consultaremedios.db.dao.CartDao
import br.com.challenge.consultaremedios.db.entity.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppAdapter(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_db").build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}