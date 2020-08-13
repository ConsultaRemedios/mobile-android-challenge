package mazer.arthur.gamingshop.data

import android.content.Context
import androidx.lifecycle.LiveData
import mazer.arthur.gamingshop.data.local.GameRoomDatabase
import mazer.arthur.gamingshop.data.remote.ApiHelper
import mazer.arthur.gamingshop.data.remote.entities.Cart

class GamesRepository(private val apiHelper: ApiHelper, context: Context){


    private val gameDao = GameRoomDatabase.getInstance(context).gameDao()

    //Remote
    suspend fun getBannersList() = apiHelper.getBanners()
    suspend fun getSpotlightList() = apiHelper.getSpotlight()
    suspend fun getGameDetails(id: Int) = apiHelper.getGameDetails(id)

    //Local Database
    suspend fun addGameCart(cart: Cart) = gameDao.addGameCart(cart)
    suspend fun removeGameCart(id: Int) = gameDao.removeGameCart(id)
    suspend fun getTotalDiscountSumCart() = gameDao.getTotalDiscountSumCart()
    suspend fun getTotalItemsCart() = gameDao.getTotalItemsCart()
    suspend fun getItemCartById(id: Int) = gameDao.getItemCartById(id)
    suspend fun isItemOnCart(id: Int) = gameDao.isItemOnCart(id)
    suspend fun getCartList() = gameDao.getCartList()

    fun getTotalDiscountSumCartLiveData(): LiveData<Int> {
        return gameDao.getTotalDiscountSumCartLiveData()
    }

    fun getTotalOriginalPriceSumCartLiveData(): LiveData<Int> {
        return gameDao.getTotalOriginalPriceSumCartLiveData()
    }

    fun getTotalItemsCartLiveData(): LiveData<Int>{
        return gameDao.getTotalItemsCartLiveData()
    }
}