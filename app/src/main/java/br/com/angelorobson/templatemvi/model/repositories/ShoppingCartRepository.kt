package br.com.angelorobson.templatemvi.model.repositories

import androidx.room.EmptyResultSetException
import br.com.angelorobson.templatemvi.model.database.dao.ShoppingCartDao
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.model.entities.GameEntity
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import io.reactivex.*
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
        private val shoppingCartDao: ShoppingCartDao
) {

    fun addItem(shoppingCart: ShoppingCart): Completable {
        return Single.fromCallable { mapToEntity(shoppingCart) }
                .flatMapCompletable { shoppingCartEntity ->
                    shoppingCartDao.add(shoppingCartEntity)
                }
    }

    fun update(shoppingCart: ShoppingCart): Completable {
        return Single.fromCallable { mapToEntity(shoppingCart) }
                .flatMapCompletable { shoppingCartEntity ->
                    shoppingCartDao.update(shoppingCartEntity)
                }
    }

    fun remove(shoppingCart: ShoppingCart): Completable {
        return Single.fromCallable { mapToEntity(shoppingCart) }
                .flatMapCompletable { shoppingCartEntity ->
                    shoppingCartDao.removeItem(shoppingCartEntity.gameEntity.idGame)
                }
    }

    fun getBy(idGame: Int): Single<ShoppingCart> {
        return shoppingCartDao.getBy(idGame).map {
            mapToDomain(it)
        }.onErrorResumeNext { error ->
            if (error is EmptyResultSetException)
                Single.just(ShoppingCart())
            else
                Single.error(error)
        }
    }

    fun clearDatabase(): Completable {
        return shoppingCartDao.clearDatabase()
    }

    fun getCount(): Single<Int> {
        return shoppingCartDao.getCount()
                .onErrorResumeNext { error ->
                    if (error is EmptyResultSetException)
                        Single.just(0)
                    else
                        Single.error(error)
                }
    }

    fun getAll(): Observable<List<ShoppingCart>> {
        return shoppingCartDao.getAll()
                .map { shoppingCartsEntities ->
                    shoppingCartsEntities.map {
                        mapToDomain(it)
                    }
                }
    }

}

private fun mapToEntity(shoppingCart: ShoppingCart): ShoppingCartEntity {
    val game = shoppingCart.spotlight
    return ShoppingCartEntity(
            id = if (shoppingCart.id != 0) shoppingCart.id else 0,
            totalWithDiscount = shoppingCart.totalWithDiscount,
            totalWithoutDiscount = shoppingCart.totalWithoutDiscount,
            quantity = shoppingCart.quantity,
            gameEntity = GameEntity(
                    idGame = game.id,
                    discount = game.discount,
                    image = game.image,
                    title = game.title,
                    price = game.price
            )
    )
}

private fun mapToDomain(cartEntity: ShoppingCartEntity): ShoppingCart {
    val game = cartEntity.gameEntity
    return ShoppingCart(
            id = cartEntity.id,
            quantity = cartEntity.quantity,
            totalWithDiscount = cartEntity.totalWithDiscount,
            totalWithoutDiscount = cartEntity.totalWithoutDiscount,
            spotlight = Spotlight(
                    id = game.idGame,
                    price = game.price,
                    discount = game.discount,
                    title = game.title,
                    image = game.image,
                    description = "",
                    publisher = "",
                    rating = 0f,
                    reviews = 0,
                    stars = 0
            )
    )

}