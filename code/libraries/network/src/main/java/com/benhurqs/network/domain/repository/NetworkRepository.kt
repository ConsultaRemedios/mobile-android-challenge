package com.benhurqs.network.domain.repository

import com.benhurqs.network.data.ChallengeService
import com.benhurqs.network.entities.Banner
import com.benhurqs.network.entities.Spotlight
import com.benhurqs.network.entities.Suggestion
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

open class NetworkRepository(
    private val apiService: ChallengeService = ChallengeService(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val type: APIServiceType
) {

    companion object {

        @Synchronized
        open fun getBanners(
            onStart: () -> Unit,
            onSuccess: (response: List<Banner>?) -> Unit,
            onFinish: () -> Unit,
            onFailure: (error: String?) -> Unit
        ) {
            NetworkRepository(type = APIServiceType.BANNER).callAPI<List<Banner>?>(
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }

        @Synchronized
        fun getSpotlight(
            onStart: () -> Unit,
            onSuccess: (response: List<Spotlight>?) -> Unit,
            onFinish: () -> Unit,
            onFailure: (error: String?) -> Unit
        ) {
            NetworkRepository(type = APIServiceType.SPOTLIGHT).callAPI<List<Spotlight>?>(
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }

        @Synchronized
        fun getSuggestion(
            query: String?,
            onStart: () -> Unit,
            onSuccess: (response: List<Suggestion>?) -> Unit,
            onFinish: () -> Unit,
            onFailure: (error: String?) -> Unit
        ) {
            NetworkRepository(type = APIServiceType.SEARCH).callAPI<List<Suggestion>?>(
                query = query,
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }

        @Synchronized
        fun getDetail(
            spotlightID: Int?,
            onStart: () -> Unit,
            onSuccess: (response: Spotlight?) -> Unit,
            onFinish: () -> Unit,
            onFailure: (error: String?) -> Unit
        ) {
            NetworkRepository(type = APIServiceType.SPOTLIGHT_DETAIL).callAPI<Spotlight?>(
                spotlightID = spotlightID,
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }

        @Synchronized
        fun checkout(
            onStart: () -> Unit,
            onSuccess: () -> Unit,
            onFinish: () -> Unit,
            onFailure: (error: String?) -> Unit
        ) {
            NetworkRepository(type = APIServiceType.CHECKOUT).callAPI<ResponseBody?>(
                 onStart = { onStart() },
                onSuccess = { onSuccess() },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }
    }

    private fun <T> getAPIService(query: String? = null, spotlightID: Int? = null): Observable<T>? {
        return when (type) {
            APIServiceType.BANNER -> apiService.getBanners() as Observable<T>
            APIServiceType.SPOTLIGHT -> apiService.getSpotlights() as Observable<T>
            APIServiceType.SEARCH -> apiService.getSearchSuggestions(query) as Observable<T>
            APIServiceType.SPOTLIGHT_DETAIL -> apiService.getDetail(spotlightID) as Observable<T>
            APIServiceType.CHECKOUT -> apiService.checkout() as Observable<T>
        }
    }


    private fun <T> callAPI(
        query: String? = null,
        spotlightID: Int? = null,
        onStart: () -> Unit,
        onSuccess: (response: T) -> Unit,
        onFinish: () -> Unit,
        onFailure: (error: String?) -> Unit
    ) {
        val observable = getAPIService<T>(query = query, spotlightID = spotlightID)

        if (observable == null) {
            onFailure("Type not found")
            onFinish()
            return
        }

        observable
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .doOnSubscribe {
                onStart()
            }
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                    onFinish()
                }

                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(bannerList: T) {
                    onSuccess(bannerList)
                }

                override fun onError(e: Throwable?) {
                    onFailure(e?.message)
                }
            })
    }



}