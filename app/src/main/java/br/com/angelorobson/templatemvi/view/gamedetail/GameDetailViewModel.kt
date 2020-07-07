package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.repositories.HomeServiceRepository
import br.com.angelorobson.templatemvi.model.repositories.ShoppingCartRepository
import br.com.angelorobson.templatemvi.view.utils.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


fun gameUpdate(
        model: GameDetailModel,
        event: GameDetailEvent
): Next<GameDetailModel, GameDetailEffect> {
    return when (event) {
        is InitialEvent -> dispatch(setOf(ObservableGameEffect(event.id)))
        is GameDetailExceptionEvent -> next(
                model.copy(
                        gameDetailResult = GameDetailResult.Error(errorMessage = event.errorMessage)
                )
        )
        is GameLoadedEvent -> next<GameDetailModel, GameDetailEffect>(model.copy(
                gameDetailResult = GameDetailResult.GameLoaded(
                        spotlight = event.spotlight
                )
        ),
                setOf(GetItemCartEffect(event.spotlight))
        )
        is AddOrRemoveItemCardEvent -> dispatch(setOf(AddOrRemoveItemCardEffect(event.spotlight)))
        is StatusShoppingCartItemEvent -> next(
                model.copy(
                        gameDetailResult = GameDetailResult.ItemCartStatusResult(event.isCartItemAdded)
                )
        )
    }
}

class GameDetailViewModel @Inject constructor(
        repository: HomeServiceRepository,
        navigator: Navigator,
        activityService: ActivityService,
        shoppingCartRepository: ShoppingCartRepository,
        idlingResource: IdlingResource
) : MobiusVM<GameDetailModel, GameDetailEvent, GameDetailEffect>(
        "GameDetailViewModel",
        Update(::gameUpdate),
        GameDetailModel(),
        RxMobius.subtypeEffectHandler<GameDetailEffect, GameDetailEvent>()
                .addTransformer(ObservableGameEffect::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()
                        repository.getGame(it.id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toObservable()
                                .map { game ->
                                    idlingResource.decrement()

                                    GameLoadedEvent(
                                            spotlight = game
                                    ) as GameDetailEvent
                                }.onErrorReturn {
                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                    activityService.activity.toast(errorMessage)
                                    GameDetailExceptionEvent(errorMessage)
                                }

                    }
                }
                .addTransformer(AddOrRemoveItemCardEffect::class.java) { upstream ->
                    upstream.switchMap {
                        shoppingCartRepository.getBy(it.spotlight!!.id)
                                .toObservable()
                                .subscribeOn(Schedulers.newThread())
                                .switchMap { itemCart ->
                                    if (itemCart.id == 0) {
                                        val shoppingCart = ShoppingCart(
                                                totalWithDiscount = it.spotlight.discount,
                                                quantity = 1,
                                                totalWithoutDiscount = it.spotlight.price,
                                                spotlight = it.spotlight
                                        )

                                        shoppingCartRepository
                                                .addItem(shoppingCart)
                                                .subscribeOn(Schedulers.newThread())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .toSingleDefault(StatusShoppingCartItemEvent(isCartItemAdded = true) as GameDetailEvent)
                                                .toObservable()
                                                .onErrorReturn {
                                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                                    activityService.activity.toast(errorMessage)
                                                    GameDetailExceptionEvent(errorMessage)
                                                }
                                    } else
                                        shoppingCartRepository.remove(itemCart)
                                                .subscribeOn(Schedulers.newThread())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .toSingleDefault(StatusShoppingCartItemEvent(isCartItemAdded = false) as GameDetailEvent)
                                                .toObservable()
                                                .onErrorReturn {
                                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                                    activityService.activity.toast(errorMessage)
                                                    GameDetailExceptionEvent(errorMessage)
                                                }
                                }
                                .onErrorReturn {
                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                    activityService.activity.toast(errorMessage)
                                    GameDetailExceptionEvent(errorMessage)
                                }


                    }
                }
                .addTransformer(GetItemCartEffect::class.java) { upstream ->
                    upstream.switchMap {
                        shoppingCartRepository.getBy(it.spotlight!!.id)
                                .toObservable()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map { shoppingCart ->
                                    if (shoppingCart.id != 0) {
                                        StatusShoppingCartItemEvent(isCartItemAdded = true) as GameDetailEvent
                                    } else
                                        StatusShoppingCartItemEvent(isCartItemAdded = false) as GameDetailEvent
                                }
                                .onErrorReturn {
                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                    activityService.activity.toast(errorMessage)
                                    GameDetailExceptionEvent(errorMessage)
                                }


                    }
                }
                .build()
)