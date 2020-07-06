package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.model.repositories.HomeServiceRepository
import br.com.angelorobson.templatemvi.model.repositories.ShoppingCartRepository
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
        is GameLoadedEvent -> next(model.copy(
                gameDetailResult = GameDetailResult.GameLoaded(
                        spotlight = event.spotlight
                )
        ))
        is AddItemCardEvent -> dispatch(setOf(AddItemCardEventEffect(event.spotlight)))
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
                                    GameDetailExceptionEvent(it.localizedMessage)
                                }

                    }
                }
                .addTransformer(AddItemCardEventEffect::class.java) { upstream ->
                    upstream.switchMap {
                        val shoppingCart = ShoppingCart(
                                total = 100.0,
                                quantity = 1,
                                spotlight = it.spotlight!!
                        )
                        shoppingCartRepository
                                .addItem(shoppingCart)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .toSingleDefault(GameDetailExceptionEvent(it.toString()))
                                .toObservable()
                                .onErrorReturn {
                                    GameDetailExceptionEvent(it.localizedMessage)
                                }
                    }
                }
                .build()
)