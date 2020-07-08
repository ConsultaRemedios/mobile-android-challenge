package br.com.angelorobson.templatemvi.view.searchgame

import br.com.angelorobson.templatemvi.model.repositories.HomeServiceRepository
import br.com.angelorobson.templatemvi.view.searchgame.SearchGameEffect.*
import br.com.angelorobson.templatemvi.view.utils.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


fun searchGameUpdate(
        model: SearchGameModel,
        event: SearchGameEvent
): Next<SearchGameModel, SearchGameEffect> {
    return when (event) {
        is InitialEvent -> dispatch(setOf(ObserverSpotlightByTermEffect(event.term)))
        is GamesFoundEvent -> next(
                model.copy(searchGameResult = SearchGameResult.GamesFoundByTerm(
                        spotlights = event.spotlights
                ))
        )
        is SearchGameByTermEvent -> dispatch(setOf(ObserverSpotlightByTermEffect(event.term)))
        is SearchGameExceptionEvent -> next(
                model.copy(searchGameResult = SearchGameResult.Error(
                        errorMessage = event.errorMessage
                ))
        )
        is GameFoundClickedEvent -> dispatch(setOf(GameClickedEffect(event.spotlight)))
    }
}

class SearchGameViewModel @Inject constructor(
        repository: HomeServiceRepository,
        navigator: Navigator,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<SearchGameModel, SearchGameEvent, SearchGameEffect>(
        "SearchGameViewModel",
        Update(::searchGameUpdate),
        SearchGameModel(),
        RxMobius.subtypeEffectHandler<SearchGameEffect, SearchGameEvent>()
                .addTransformer(ObserverSpotlightByTermEffect::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()
                        repository.searchByTerm(it.term)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map { gamesFound ->
                                    idlingResource.decrement()

                                    GamesFoundEvent(
                                            gamesFound
                                    ) as SearchGameEvent
                                }.onErrorReturn {
                                    val errorMessage = HandlerErrorRemoteDataSource.validateStatusCode(it)
                                    activityService.activity?.toastWithResourceString(errorMessage.toInt())
                                    SearchGameExceptionEvent(errorMessage)
                                }

                    }
                }
                .addConsumer(GameClickedEffect::class.java) { consumer ->
                    navigator.to(SearchGameFragmentDirections.toGameDetailFragment(consumer.spotlight.id))
                }
                .build()
)