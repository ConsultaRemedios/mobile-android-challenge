package com.games.ecommerce.main.data.repository

import com.games.ecommerce.main.data.db.dao.ShoppingGameDao
import com.games.ecommerce.main.data.db.toShoppingGame
import com.games.ecommerce.main.data.db.toShoppingGameEntity
import com.games.ecommerce.main.data.model.ShoppingGame

interface ShoppingRepository {
    suspend fun saveShoppingGame(shoppingGame: ShoppingGame): StatusDB
    suspend fun getShoppingGames(): List<ShoppingGame>
    suspend fun getShoppingById(id: Int): ShoppingGame
    suspend fun removeShoppingGame(id: Int): StatusDB
    suspend fun removeAllShoppingGames()
    suspend fun getTotalAmount(): Int?
}

class ShoppingRepositoryImpl(
    private val shoppingGameDao: ShoppingGameDao
) : ShoppingRepository {
    override suspend fun saveShoppingGame(shoppingGame: ShoppingGame): StatusDB {
        return try {
            shoppingGameDao.save(shoppingGame.toShoppingGameEntity())
            StatusDB.SUCCESS
        } catch (ex: Exception) {
            StatusDB.ERROR
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
        return try {
            shoppingGameDao.removeGame(id)
            StatusDB.SUCCESS
        } catch (ex: Exception) {
            StatusDB.ERROR
        }
    }

    override suspend fun removeAllShoppingGames() {
        shoppingGameDao.removeAll()
    }

    override suspend fun getTotalAmount(): Int? {
        return shoppingGameDao.getTotalAmount() ?: return 0
    }
}

enum class StatusDB {
    SUCCESS,
    ERROR
}