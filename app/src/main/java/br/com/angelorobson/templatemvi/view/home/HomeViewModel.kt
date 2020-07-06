package br.com.angelorobson.templatemvi.view.home

import br.com.angelorobson.templatemvi.model.repositories.HomeServiceRepository
import br.com.angelorobson.templatemvi.view.home.HomeEffect.*
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


fun homeUpdate(
        model: HomeModel,
        event: HomeEvent
): Next<HomeModel, HomeEffect> {
    return when (event) {
        is InitialEvent -> dispatch(setOf(ObserverBanner))
        is BannersLoadedEvent -> next(
                model.copy(homeResult = HomeResult.BannerLoaded(event.banners)),
                setOf(ObserverSpotlight)
        )
        is SpotlightLoadedEvent -> next(model.copy(
                homeResult = HomeResult.SpotlightsLoaded(event.spotlights)
        ))
        is HomeExceptionEvent -> next(model.copy(
                homeResult = HomeResult.Error(
                        errorMessage = event.errorMessage
                )
        ))
        is GameClickedEvent -> dispatch(setOf(GameClickedEffect(spotlight = event.spotlight)))
        SearchViewClickedEvent -> dispatch(setOf(SearchViewClickedEffect))
        is BannerClickedEvent -> dispatch(setOf(BannerClickedEffect(event.url)))
    }
}

class HomeViewModel @Inject constructor(
        repository: HomeServiceRepository,
        navigator: Navigator,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<HomeModel, HomeEvent, HomeEffect>(
        "HomeViewModel",
        Update(::homeUpdate),
        HomeModel(),
        RxMobius.subtypeEffectHandler<HomeEffect, HomeEvent>()
                .addTransformer(ObserverBanner::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()
                        repository.getAll()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map {
                                    idlingResource.decrement()

                                    BannersLoadedEvent(
                                            banners = it
                                    ) as HomeEvent
                                }.onErrorReturn {
                                    HomeExceptionEvent(it.localizedMessage)
                                }

                    }
                }.addTransformer(ObserverSpotlight::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()
                        repository.getAllSpotlights()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map {
                                    idlingResource.decrement()

                                    SpotlightLoadedEvent(
                                            spotlights = it
                                    ) as HomeEvent
                                }
                                .onErrorReturn {
                                    HomeExceptionEvent(it.localizedMessage)
                                }

                    }
                }
                .addConsumer(GameClickedEffect::class.java) { effect ->
                    navigator.to(HomeFragmentDirections.gameDetailFragment(effect.spotlight.id))
                }
                .addAction(SearchViewClickedEffect::class.java) {
                    navigator.to(HomeFragmentDirections.searchGameFragment())
                }
                .addConsumer(BannerClickedEffect::class.java) { effect ->
                    navigator.to(HomeFragmentDirections.webViewFragment(effect.url))

                }
                .build()
)