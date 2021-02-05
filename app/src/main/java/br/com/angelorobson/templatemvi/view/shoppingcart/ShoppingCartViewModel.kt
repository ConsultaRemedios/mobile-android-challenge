package br.com.angelorobson.templatemvi.view.shoppingcart

import android.widget.Toast
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.model.repositories.PurchaseRepository
import br.com.angelorobson.templatemvi.model.repositories.ShoppingCartRepository
import br.com.angelorobson.templatemvi.view.gamedetail.GameDetailEvent
import br.com.angelorobson.templatemvi.view.gamedetail.GameDetailExceptionEvent
import br.com.angelorobson.templatemvi.view.gamedetail.StatusShoppingCartItemEvent
import br.com.angelorobson.templatemvi.view.shoppingcart.ShoppingCartEffect.*
import br.com.angelorobson.templatemvi.view.utils.*
import br.com.angelorobson.templatemvi.view.utils.HandlerErrorRemoteDataSource.validateStatusCode
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
        is ButtonPurchaseClickedEvent -> dispatch(setOf(ButtonPurchaseEffect(event.shoppingItemsCart, total = event.total)))
        is PurchaseSuccessfully -> dispatch(setOf(ClearDatabaseEffect))
        is ImageItemClicked -> dispatch(setOf(ImageItemClickedEffect(event.shoppingCart)))
    }
}

class ShoppingCartViewModel @Inject constructor(
        repository: ShoppingCartRepository,
        navigator: Navigator,
        purchaseRepository: PurchaseRepository,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<ShoppingCartModel, ShoppingCartEvent, ShoppingCartEffect>(
        "ShoppingCartViewModel",
        Update(::shoppingCartUpdate),
        ShoppingCartModel(),
        RxMobius.subtypeEffectHandler<ShoppingCartEffect, ShoppingCartEvent>()
                .addTransformer(ObserverShoppingCart::class.java) { upstream ->
                    upstream.switchMap {
                        repository.getAll()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map { shoppingCarts ->
                                    var totalWithoutDiscount = 0.0
                                    var totalWithDiscount = 0.0
                                    var freteValue = 0.0
                                    var totalQuantity = 0

                                    if (shoppingCarts.isNotEmpty()) {
                                        totalWithoutDiscount = shoppingCarts.sumByDouble { it.totalWithoutDiscount }
                                        totalWithDiscount = shoppingCarts.sumByDouble { it.totalWithDiscount }
                                        totalQuantity = shoppingCarts.sumBy { it.quantity }

                                        freteValue = totalQuantity * 10.toDouble()

                                        if (totalWithDiscount > 250) {
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
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity?.toast(errorMessage)
                                    ShoppingCartExceptionsEvent(errorMessage)
                                }

                    }
                }
                .addTransformer(AddButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.getBy(it.shoppingCart.spotlight.id)
                                .toObservable()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
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
                                                val errorMessage = validateStatusCode(it)
                                                activityService.activity?.toastWithResourceString(errorMessage.toInt())
                                                ShoppingCartExceptionsEvent(errorMessage)
                                            }
                                }.onErrorReturn {
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity?.toast(errorMessage)
                                    ShoppingCartExceptionsEvent(errorMessage)
                                }

                    }
                }
                .addTransformer(RemoveButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.getBy(it.shoppingCart.spotlight.id)
                                .toObservable()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
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
                                                val errorMessage = validateStatusCode(it)
                                                activityService.activity?.toastWithResourceString(errorMessage.toInt())

                                                ShoppingCartExceptionsEvent(errorMessage)
                                            }
                                }
                                .onErrorReturn {
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity?.toast(errorMessage)
                                    ShoppingCartExceptionsEvent(errorMessage)
                                }

                    }
                }
                .addTransformer(ClearButtonItemClickedEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.remove(it.shoppingCart)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toSingleDefault(InitialEvent as ShoppingCartEvent)
                                .toObservable()
                                .onErrorReturn {
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity?.toastWithResourceString(errorMessage.toInt())

                                    ShoppingCartExceptionsEvent(errorMessage)
                                }
                    }
                }
                .addTransformer(ButtonPurchaseEffect::class.java) { upstream ->
                    upstream.switchMap {
                        val itemsIds = it.shoppingItemsCart.map { it.spotlight.id }
                        val total = it.total

                        purchaseRepository.checkout(itemsIds, total)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toSingleDefault(PurchaseSuccessfully as ShoppingCartEvent)
                                .doAfterSuccess {
                                    activityService.activity?.toast(activityService.activity?.getString(R.string.purchase_sucessfully))
                                    navigator.to(ShoppingCardFragmentDirections.homeFragment())
                                }
                                .toObservable()
                                .onErrorReturn {
                                    activityService.activity?.toast(activityService.activity?.getString(R.string.purchase_error))
                                    val errorMessage = validateStatusCode(it)
                                    ShoppingCartExceptionsEvent(errorMessage)
                                }

                    }
                }
                .addTransformer(ClearDatabaseEffect::class.java) { upstream ->
                    upstream.switchMap {
                        repository.clearDatabase()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toSingleDefault(InitialEvent as ShoppingCartEvent)
                                .toObservable()
                                .onErrorReturn {
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity?.toastWithResourceString(errorMessage.toInt())
                                    ShoppingCartExceptionsEvent(errorMessage)
                                }
                    }
                }
                .addConsumer(ImageItemClickedEffect::class.java) { consumer ->
                    val game = consumer.shoppingCart.spotlight
                    navigator.to(ShoppingCardFragmentDirections.gameDetailFragment(game.id))
                }
                .build()
)