package com.example.cheesecakenews.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.cheesecakenews.model.Game
import com.example.mobile_android_challenge.model.ItemCart

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private fun Cursor.getString(column: String): String = getString(getColumnIndex(column))

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_ENTRIES_CART)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_ENTRIES_CART)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertGames(game: Game): Boolean = writableDatabase.use { db ->
        val values = ContentValues().apply {
            put(DBContract.GamesEntry.COLUMN_ID, game.id)
            put(DBContract.GamesEntry.COLUMN_NAME, game.name)
            put(DBContract.GamesEntry.COLUMN_PLATAFORM, game.platform)
            put(DBContract.GamesEntry.COLUMN_PRICE, game.price)
            put(DBContract.GamesEntry.COLUMN_IMAGE, game.image)
        }
        val newRowId = db.insert(DBContract.GamesEntry.TABLE_NAME, null, values)

        newRowId > -1
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCart(game: ItemCart): Boolean = writableDatabase.use { db ->
        val values = ContentValues().apply {
            put(DBContract.CartEntry.COLUMN_ID, game.id)
            put(DBContract.CartEntry.COLUMN_NAME, game.name)
            put(DBContract.CartEntry.COLUMN_PLATAFORM, game.platform)
            put(DBContract.CartEntry.COLUMN_PRICE, game.price)
            put(DBContract.CartEntry.COLUMN_IMAGE, game.image)
            put(DBContract.CartEntry.COLUMN_QUANTITY, game.quantity)
        }
        val newRowId = db.insert(DBContract.CartEntry.TABLE_NAME, null, values)

        newRowId > -1
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteGames(newsTitle: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.GamesEntry.COLUMN_NAME + " LIKE ?"
        val selectionArgs = arrayOf(newsTitle)
        db.delete(DBContract.GamesEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readGames(newsTitle: String): ArrayList<Game> =
        writableDatabase.use { db ->
            val selectQuery =
                "SELECT  * FROM ${DBContract.GamesEntry.TABLE_NAME} WHERE ${DBContract.GamesEntry.COLUMN_NAME} = $newsTitle"
            db.rawQuery(selectQuery, null).use { cursor ->
                val news = ArrayList<Game>()
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val id = cursor.getString(DBContract.GamesEntry.COLUMN_ID)
                        val name = cursor.getString(DBContract.GamesEntry.COLUMN_NAME)
                        val platform = cursor.getString(DBContract.GamesEntry.COLUMN_PLATAFORM)
                        val price = cursor.getString(DBContract.GamesEntry.COLUMN_PRICE)
                        val image = cursor.getString(DBContract.GamesEntry.COLUMN_IMAGE)

                        news.add(
                            Game(
                                id.toLong(),
                                name,
                                platform,
                                price.toDouble(),
                                image
                            )
                        )
                        cursor.moveToNext()
                    }
                }
                news
            }
        }

    fun readItemCart(id: Long): ArrayList<ItemCart> =
        writableDatabase.use { db ->
            val selectQuery =
                "SELECT  * FROM ${DBContract.CartEntry.TABLE_NAME} WHERE ${DBContract.CartEntry.COLUMN_ID} = $id"
            db.rawQuery(selectQuery, null).use { cursor ->
                val news = ArrayList<ItemCart>()
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val id = cursor.getString(DBContract.CartEntry.COLUMN_ID)
                        val name = cursor.getString(DBContract.CartEntry.COLUMN_NAME)
                        val platform = cursor.getString(DBContract.CartEntry.COLUMN_PLATAFORM)
                        val price = cursor.getString(DBContract.CartEntry.COLUMN_PRICE)
                        val image = cursor.getString(DBContract.CartEntry.COLUMN_IMAGE)
                        val quantity = cursor.getString(DBContract.CartEntry.COLUMN_QUANTITY)
                        news.add(
                            ItemCart(
                                id.toLong(),
                                name,
                                price.toDouble(),
                                platform,
                                image,
                                quantity.toInt()
                            )
                        )
                        cursor.moveToNext()
                    }
                }
                news
            }
     }

    fun readItemsCart(): ArrayList<ItemCart> =
        writableDatabase.use { db ->
            val selectQuery =
                "SELECT  * FROM ${DBContract.CartEntry.TABLE_NAME} "
            db.rawQuery(selectQuery, null).use { cursor ->
                val news = ArrayList<ItemCart>()
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        val id = cursor.getString(DBContract.CartEntry.COLUMN_ID)
                        val name = cursor.getString(DBContract.CartEntry.COLUMN_NAME)
                        val platform = cursor.getString(DBContract.CartEntry.COLUMN_PLATAFORM)
                        val price = cursor.getString(DBContract.CartEntry.COLUMN_PRICE)
                        val image = cursor.getString(DBContract.CartEntry.COLUMN_IMAGE)
                        val quantity = cursor.getString(DBContract.CartEntry.COLUMN_QUANTITY)
                        news.add(
                            ItemCart(
                                id.toLong(),
                                name,
                                price.toDouble(),
                                platform,
                                image,
                                quantity.toInt()
                            )
                        )
                        cursor.moveToNext()
                    }
                }
                news
            }
     }

    fun readAllGames(): ArrayList<Game> {
        val games = ArrayList<Game>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.GamesEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var title: String
        var website: String
        var authors: String
        var date: String
        var content: String
        var image_url: String
        var is_read: String

            while (cursor.moveToNext()) {
                val id = cursor.getString(DBContract.GamesEntry.COLUMN_ID)
                val name = cursor.getString(DBContract.GamesEntry.COLUMN_NAME)
                val platform = cursor.getString(DBContract.GamesEntry.COLUMN_PLATAFORM)
                val price = cursor.getString(DBContract.GamesEntry.COLUMN_PRICE)
                val image = cursor.getString(DBContract.GamesEntry.COLUMN_IMAGE)
                games.add(Game(id.toLong(), name, platform, price.toDouble(), image))
        }
        return games
    }

    fun readAllItemsCart(): ArrayList<ItemCart> {
        val itemsCart = ArrayList<ItemCart>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.CartEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        val id: String
        val name: String
        val price: String
        val platform: String
        val image: String
        var quantity: String

            while (cursor.moveToNext()) {
                val id = cursor.getString(DBContract.CartEntry.COLUMN_ID)
                val name = cursor.getString(DBContract.CartEntry.COLUMN_NAME)
                val price = cursor.getString(DBContract.CartEntry.COLUMN_PRICE)
                val platform = cursor.getString(DBContract.CartEntry.COLUMN_PLATAFORM)
                val image = cursor.getString(DBContract.CartEntry.COLUMN_IMAGE)
                val quantity = cursor.getString(DBContract.CartEntry.COLUMN_QUANTITY)
                itemsCart.add(ItemCart(id.toLong(), name, price.toDouble(), platform, image, quantity.toInt()))
        }
        return itemsCart
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.GamesEntry.TABLE_NAME + " (" +
                    DBContract.GamesEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    DBContract.GamesEntry.COLUMN_NAME + " TEXT," +
                    DBContract.GamesEntry.COLUMN_PLATAFORM + " TEXT," +
                    DBContract.GamesEntry.COLUMN_PRICE + " TEXT," +
                    DBContract.GamesEntry.COLUMN_IMAGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.GamesEntry.TABLE_NAME

        private val SQL_CREATE_ENTRIES_CART =
            "CREATE TABLE " + DBContract.CartEntry.TABLE_NAME + " (" +
                    DBContract.CartEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    DBContract.CartEntry.COLUMN_NAME + " TEXT," +
                    DBContract.CartEntry.COLUMN_PRICE + " TEXT," +
                    DBContract.CartEntry.COLUMN_PLATAFORM + " TEXT," +
                    DBContract.CartEntry.COLUMN_IMAGE + " TEXT," +
                    DBContract.CartEntry.COLUMN_QUANTITY + " TEXT)"

        private val SQL_DELETE_ENTRIES_CART = "DROP TABLE IF EXISTS " + DBContract.CartEntry.TABLE_NAME
    }

    @Throws(SQLiteConstraintException::class)
    fun readItem(games: Game): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DBContract.GamesEntry.COLUMN_ID, games.id)
        values.put(DBContract.GamesEntry.COLUMN_NAME, games.name)
        values.put(DBContract.GamesEntry.COLUMN_PLATAFORM, games.platform)
        values.put(DBContract.GamesEntry.COLUMN_PRICE, games.price)
        values.put(DBContract.GamesEntry.COLUMN_IMAGE, games.image)

      db.update(DBContract.GamesEntry.TABLE_NAME, values, DBContract.GamesEntry.COLUMN_NAME + " = ?",
            arrayOf(games.name)
        )
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateCartItem(itemCart: ItemCart): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DBContract.CartEntry.COLUMN_ID, itemCart.id)
        values.put(DBContract.CartEntry.COLUMN_NAME, itemCart.name)
        values.put(DBContract.CartEntry.COLUMN_PRICE, itemCart.price)
        values.put(DBContract.CartEntry.COLUMN_PLATAFORM, itemCart.platform)
        values.put(DBContract.CartEntry.COLUMN_IMAGE, itemCart.image)
        values.put(DBContract.CartEntry.COLUMN_QUANTITY, itemCart.quantity)

      db.update(DBContract.CartEntry.TABLE_NAME, values, DBContract.GamesEntry.COLUMN_NAME + " = ?",
            arrayOf(itemCart.name)
        )
        return true
    }
}