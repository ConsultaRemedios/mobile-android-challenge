package br.com.angelorobson.templatemvi.model.repositories

import br.com.angelorobson.templatemvi.model.database.dao.ShoppingCartDao
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.model.entities.GameEntity
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
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
                    shoppingCartDao.removeItem(shoppingCartEntity)
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
            total = shoppingCart.total,
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
            total = cartEntity.total,
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