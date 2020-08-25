package com.games.ecommerce.main.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.games.ecommerce.main.data.db.ShoppingGameEntity

@Dao
interface ShoppingGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(shoppingGame: ShoppingGameEntity)

    @Query("SELECT * FROM shopping WHERE id = :id")
    suspend fun getById(id: Int): ShoppingGameEntity

    @Query("SELECT * FROM shopping")
    suspend fun getAllGames(): List<ShoppingGameEntity>

    @Query("DELETE FROM shopping where id = :id")
    suspend fun removeGame(id: Int)

    @Query("SELECT SUM(amount) FROM shopping")
    suspend fun getTotalAmount(): Int?

    @Query("DELETE FROM shopping")
    suspend fun removeAll()
}