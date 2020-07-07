package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.repositories.ShoppingCartRepository
import br.com.angelorobson.templatemvi.view.gamedetail.GameDetailEvent
import br.com.angelorobson.templatemvi.view.gamedetail.GameDetailExceptionEvent
import br.com.angelorobson.templatemvi.view.gamedetail.StatusShoppingCartItemEvent
import br.com.angelorobson.templatemvi.view.shoppingcart.ShoppingCartEffect.*
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
import io.reactivex.internal.operators.single.SingleInternalHelper.toObservable
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
                                totalQuantity = event.totalQuantity,
                                freteValue = event.freteValue
                        )
                )
        )
        is ShoppingCartExceptionsEvent -> next(
                model.copy(
                        shoppingCartResult = ShoppingCartModelResult.Error(errorMessage = event.errorMessage)
                )
        )
        is RemoveButtonItemClicked -> dispatch(setOf(RemoveButtonItemClickedEffect(event.shoppingCart)))
        is AddButtonItemClicked -> dispatch(setOf(AddButtonItemClickedEffect(event.shoppingCart)))
        is ClearButtonItemClicked -> dispatch(setOf(ClearButtonItemClickedEffect(event.shoppingCart)))
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
                                    var totalWithoutDiscount = 0.0
                                    var totalWithDiscount = 0.0
                                    var freteValue = 0.0
                                    var totalQuantity = 0

                                    if (shoppingCarts.isNotEmpty()) {
                                        totalWithoutDiscount = shoppingCarts.map {
                                            it.totalWithoutDiscount
                                        }.reduce { acc, d ->
                                            acc + d
                                        }

                                        totalWithDiscount = shoppingCarts.map {
                                            it.totalWithDiscount
                                        }.reduce { acc, d ->
                                            acc + d
                                        }

                                        totalQuantity = shoppingCarts.map { it.quantity }.reduce { acc, d ->
                                            acc + d
                                        }

                                        freteValue = totalQuantity * 10.toDouble()

                                        if (freteValue > 250) {
                                            freteValue = 0.0
                                        }
                                    }

                                    ShoppingItemsCartLoadedEvent(
                                            shoppingCarts,
                                            totalWithDiscount = totalWithDiscount,
                                            totalWithoutDiscount = totalWithoutDiscount,
                                            totalQuantity = totalQuantity,
                                            freteValue = freteValue) as ShoppingCartEvent
                                }.onErrorReturn {
                                    ShoppingCartExceptionsEvent(it.localizedMessage)
                                }

                    }
                }
                .addTransformer(AddButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.getBy(it.shoppingCart.spotlight.id)
                                .toObservable()
                                .subscribeOn(Schedulers.newThread())
                                .switchMap { itemCart ->
                                    itemCart.totalWithDiscount += itemCart.spotlight.discount
                                    itemCart.totalWithoutDiscount += itemCart.spotlight.price
                                    itemCart.quantity += 1
                                    repository.update(itemCart)
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .toSingleDefault(InitialEvent as ShoppingCartEvent)
                                            .toObservable()
                                            .onErrorReturn {
                                                ShoppingCartExceptionsEvent(it.localizedMessage)
                                            }
                                }

                    }
                }
                .addTransformer(RemoveButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.getBy(it.shoppingCart.spotlight.id)
                                .toObservable()
                                .subscribeOn(Schedulers.newThread())
                                .switchMap { itemCart ->
                                    itemCart.totalWithDiscount -= itemCart.spotlight.discount
                                    itemCart.totalWithoutDiscount -= itemCart.spotlight.price
                                    itemCart.quantity -= 1
                                    repository.update(itemCart)
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .toSingleDefault(InitialEvent as ShoppingCartEvent)
                                            .toObservable()
                                            .onErrorReturn {
                                                ShoppingCartExceptionsEvent(it.localizedMessage)
                                            }
                                }

                    }
                }
                .addTransformer(ClearButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.remove(it.shoppingCart)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toSingleDefault(InitialEvent as ShoppingCartEvent)
                                .toObservable()
                                .onErrorReturn {
                                    ShoppingCartExceptionsEvent(it.localizedMessage)
                                }
                    }
                }
                .build()
)