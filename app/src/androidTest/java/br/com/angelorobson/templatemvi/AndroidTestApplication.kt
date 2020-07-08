package br.com.angelorobson.templatemvi

import br.com.angelorobson.templatemvi.di.DaggerTestComponent
import br.com.angelorobson.templatemvi.model.builders.ShoppingCartBuilder
import br.com.angelorobson.templatemvi.model.builders.ShoppingCartEntityBuilder
import br.com.angelorobson.templatemvi.model.database.dao.ShoppingCartDao
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity
import br.com.angelorobson.templatemvi.view.App
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class AndroidTestApplication : App() {

    val shoppingCartItemsSubject = PublishSubject.create<List<ShoppingCartEntity>>()

    private val shoppingCartDao: ShoppingCartDao = object : ShoppingCartDao() {
        override fun getAll(): Observable<List<ShoppingCartEntity>> {
            return shoppingCartItemsSubject
        }

        override fun getBy(idGame: Int): Single<ShoppingCartEntity> {
            val shoppingCartEntity = ShoppingCartEntityBuilder.Builder().oneShoppingCartEntity().build()
            return Single.just(shoppingCartEntity)
        }

        override fun add(shoppingCartEntity: ShoppingCartEntity): Completable {
            return Completable.complete()
        }

        override fun update(shoppingCartEntity: ShoppingCartEntity): Completable {
            return Completable.complete()
        }

        override fun removeItem(idGame: Int): Completable {
            return Completable.complete()
        }

        override fun clearDatabase(): Completable {
            return Completable.complete()
        }

        override fun getCount(): Single<Int> {
            return Single.just(1)
        }
    }

    override val component by lazy {
        DaggerTestComponent.builder()
                .context(this)
                .shoppingCartDao(shoppingCartDao)
                .build()
    }
}