package br.com.angelorobson.templatemvi.model.repositories

import androidx.room.EmptyResultSetException
import br.com.angelorobson.templatemvi.model.database.dao.ShoppingCartDao
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.utils.mapToDomain
import br.com.angelorobson.templatemvi.model.utils.mapToEntity
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