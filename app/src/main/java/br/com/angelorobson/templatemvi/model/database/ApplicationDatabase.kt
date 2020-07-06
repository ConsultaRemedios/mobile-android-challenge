package br.com.angelorobson.templatemvi.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.angelorobson.templatemvi.model.database.dao.ShoppingCartDao
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity


@Database(
        entities = [ShoppingCartEntity::class],
        version = 1,
        exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun shoppingCartDao(): ShoppingCartDao


}