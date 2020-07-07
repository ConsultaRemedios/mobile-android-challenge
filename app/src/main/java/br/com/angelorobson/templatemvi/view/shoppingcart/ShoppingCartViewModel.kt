package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.repositories.ShoppingCartRepository
import br.com.angelorobson.templatemvi.view.shoppingcart.ShoppingCartEffect.ObserverShoppingCart
import br.com.angelorobson.templatemvi.view.utils.ActivityService
import br.com.angelorobson.templatemvi.view.utils.IdlingResource
import br.com.angelorobson.templatemvi.view.utils.MobiusVM
import br.com.angelorobson.templatemvi.view.utils.Navigator
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


fun shoppingCartUpdate(
        model: ShoppingCartModel,
        event: ShoppingCartEvent
): Next<ShoppingCartModel, ShoppingCartEffect> {
    return when (event) {
        InitialEvent -> dispatch(setOf(ObserverShoppingCart))
        is ShoppingItemsCartLoadedEvent -> next(
                model.copy(
                        shoppingCartResult = ShoppingCartModelResult.ShoppingCartItemsLoaded(
                                shoppingItemsCart = event.shoppingItemsCart,
                                totalWithoutDiscount = event.totalWithoutDiscount,
                                totalWithDiscount = event.totalWithDiscount,
                                itemsSize = event.itemsSize,
                                freteValue = event.freteValue
                        )
                )
        )
        is ShoppingCartExceptionsEvent -> next(
                model.copy(
                        shoppingCartResult = ShoppingCartModelResult.Error(errorMessage = event.errorMessage)
                )
        )
    }
}

class ShoppingCartViewModel @Inject constructor(
        repository: ShoppingCartRepository,
        navigator: Navigator,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<ShoppingCartModel, ShoppingCartEvent, ShoppingCartEffect>(
        "ShoppingCartViewModel",
        Update(::shoppingCartUpdate),
        ShoppingCartModel(),
        RxMobius.subtypeEffectHandler<ShoppingCartEffect, ShoppingCartEvent>()
                .addTransformer(ObserverShoppingCart::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()
                        repository.getAll()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map { shoppingCarts ->
                                    idlingResource.decrement()

                                    val totalWithoutDiscount = shoppingCarts.map {
                                        it.totalWithoutDiscount
                                    }.reduce { acc, d ->
                                        acc + d
                                    }

                                    val totalWithDiscount = shoppingCarts.map {
                                        it.totalWithDiscount
                                    }.reduce { acc, d ->
                                        acc + d
                                    }

                                    var freteValue = 0.0
                                    repeat(shoppingCarts.size) {
                                        freteValue += 10
                                    }


                                    if (freteValue > 250) {
                                        freteValue = 0.0
                                    }

                                    ShoppingItemsCartLoadedEvent(
                                            shoppingCarts,
                                            totalWithDiscount = totalWithDiscount,
                                            totalWithoutDiscount = totalWithoutDiscount,
                                            itemsSize = shoppingCarts.size,
                                            freteValue = freteValue) as ShoppingCartEvent
                                }.onErrorReturn {
                                    ShoppingCartExceptionsEvent(it.localizedMessage)
                                }

                    }
                }
                .build()
)