package mazer.arthur.gamingshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mazer.arthur.gamingshop.data.remote.entities.Cart

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameCart(cart: Cart): Long

    @Query("UPDATE Cart SET quantity=:quantity WHERE idGameDetails = :id")
    suspend fun updateGameQuantity(id: Int, quantity: Int)

    @Query("DELETE FROM Cart WHERE idGameDetails = :id")
    suspend fun removeGameCart(id: Int)

    @Query("SELECT SUM(quantity*price) FROM Cart")
    suspend fun getTotalOriginalSumCart(): Int?

    @Query("SELECT SUM(quantity*discount) FROM Cart")
    suspend fun getTotalDiscountSumCart(): Int?

    @Query("SELECT SUM(quantity*discount) FROM Cart")
    fun getTotalDiscountSumCartLiveData(): LiveData<Int>

    @Query("SELECT SUM(quantity*price) FROM Cart")
    fun getTotalOriginalPriceSumCartLiveData(): LiveData<Int>

    @Query("SELECT idGameDetails FROM Cart WHERE idGameDetails=:id")
    suspend fun getItemCartById(id: Int): Int?

    @Query("SELECT Count(idGameDetails) FROM Cart WHERE idGameDetails=:id")
    suspend fun isItemOnCart(id: Int): Int

    @Query("SELECT * FROM Cart")
    suspend fun getCartList(): List<Cart>

    @Query("SELECT SUM(quantity) FROM CART")
    suspend fun getTotalItemsCart(): Int?

    @Query("SELECT SUM(quantity) FROM CART")
    fun getTotalItemsCartLiveData(): LiveData<Int>


}