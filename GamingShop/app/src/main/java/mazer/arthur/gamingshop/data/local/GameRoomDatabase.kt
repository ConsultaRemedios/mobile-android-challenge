package mazer.arthur.gamingshop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mazer.arthur.gamingshop.data.remote.entities.Cart


@Database(entities = [Cart::class], version = 1, exportSchema = false)
abstract class GameRoomDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao


    companion object {
        private var INSTANCE: GameRoomDatabase? = null

        fun getInstance(context: Context): GameRoomDatabase? {
            createNewInstanceIfNecessary(context)
            return INSTANCE
        }

        private fun createNewInstanceIfNecessary(context: Context) {
            synchronized(this) {
                if (INSTANCE == null) {
                    val builder = Room.databaseBuilder(context, GameRoomDatabase::class.java, "gaming_shop.sqlite")
                    INSTANCE = builder.build()
                }
            }
        }

    }
}
