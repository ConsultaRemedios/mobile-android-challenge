package br.com.angelorobson.templatemvi.model.database.dao

import androidx.room.*
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import io.reactivex.*

@Dao
abstract class ShoppingCartDao {

    @Query("SELECT * FROM ShoppingCartEntity")
    abstract fun getAll(): Observable<List<ShoppingCartEntity>>

    @Query("SELECT * FROM ShoppingCartEntity WHERE idGame = :idGame")
    abstract fun getBy(idGame: Int): Single<ShoppingCartEntity>

    @Insert
    abstract fun add(shoppingCartEntity: ShoppingCartEntity): Completable

    @Update
    abstract fun update(shoppingCartEntity: ShoppingCartEntity): Completable

    @Query("DELETE FROM ShoppingCartEntity WHERE idGame = :idGame")
    abstract fun removeItem(idGame: Int): Completable

    @Query("DELETE FROM ShoppingCartEntity")
    abstract fun clearDatabase(): Completable

    @Query("SELECT SUM(quantity) FROM ShoppingCartEntity")
    abstract fun getCount(): Single<Int>

}