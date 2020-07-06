package br.com.angelorobson.templatemvi.model.database.dao

import androidx.room.*
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class ShoppingCartDao {

    @Query("SELECT * FROM ShoppingCartEntity")
    abstract fun getAll(): Observable<List<ShoppingCartEntity>>

    @Insert
    abstract fun add(shoppingCartEntity: ShoppingCartEntity): Completable

    @Update
    abstract fun update(shoppingCartEntity: ShoppingCartEntity): Completable

    @Delete
    abstract fun removeItem(shoppingCartEntity: ShoppingCartEntity): Completable

    @Query("SELECT COUNT(*) FROM ShoppingCartEntity")
    abstract fun getCount(): Observable<Int>

}