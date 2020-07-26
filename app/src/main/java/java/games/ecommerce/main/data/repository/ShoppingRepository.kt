package java.games.ecommerce.main.data.repository

import android.os.AsyncTask
import android.util.TimeUtils
import java.games.ecommerce.main.data.db.dao.ShoppingGameDao
import java.games.ecommerce.main.data.db.toShoppingGame
import java.games.ecommerce.main.data.db.toShoppingGameEntity
import java.games.ecommerce.main.data.model.ShoppingGame
import java.lang.Exception

interface ShoppingRepository {
    suspend fun saveShoppingGame(shoppingGame: ShoppingGame) : StatusDB
    suspend fun getShoppingGames(): List<ShoppingGame>
    suspend fun getShoppingById(id: Int): ShoppingGame
    suspend fun removeShoppingGame(id: Int) : StatusDB
    suspend fun removeAllShopingGames()
    suspend fun getTotalAmount(): Int?
}

class ShoppingRepositoryImpl(
    private val shoppingGameDao: ShoppingGameDao
) : ShoppingRepository {
    override suspend fun saveShoppingGame(shoppingGame: ShoppingGame) : StatusDB {
        try {
            shoppingGameDao.save(shoppingGame.toShoppingGameEntity())
            return StatusDB.SUCCESS
        } catch (ex: Exception) {
            return StatusDB.ERROR
        }
    }

    override suspend fun getShoppingGames(): List<ShoppingGame> {
        return shoppingGameDao.getAllGames().map {
            it.toShoppingGame()
        }
    }

    override suspend fun getShoppingById(id: Int): ShoppingGame {
        return shoppingGameDao.getById(id)?.toShoppingGame()
    }

    override suspend fun removeShoppingGame(id: Int): StatusDB {
        try {
            shoppingGameDao.removeGame(id)
            return StatusDB.SUCCESS
        } catch (ex: Exception) {
            return StatusDB.ERROR
        }
    }

    override suspend fun removeAllShopingGames() {
        shoppingGameDao.removeAll()
    }

    override suspend fun getTotalAmount(): Int? {
        val result = shoppingGameDao.getTotalAmount()
        if(result == null) return 0
        return result
    }
}

enum class StatusDB {
    SUCCESS,
    ERROR
}