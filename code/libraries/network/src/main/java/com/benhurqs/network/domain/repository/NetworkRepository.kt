package com.benhurqs.network.domain.repository

import com.benhurqs.network.data.ChallengeService
import com.benhurqs.network.entities.Banner
import com.benhurqs.network.entities.Spotlight
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class NetworkRepository(
    private val apiService: ChallengeService = ChallengeService(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val type: APIServiceType
) {

    companion object{

        @Synchronized
        fun getBanners(onStart: () -> Unit, onSuccess: (response: List<Banner>?) -> Unit, onFinish: () -> Unit, onFailure: (error: String?) -> Unit) {
            NetworkRepository(type = APIServiceType.BANNER).callAPI<List<Banner>?>(
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }

        @Synchronized
        fun getSpotlight(onStart: () -> Unit, onSuccess: (response: List<Spotlight>?) -> Unit, onFinish: () -> Unit, onFailure: (error: String?) -> Unit) {
            NetworkRepository(type = APIServiceType.SPOTLIGHT).callAPI<List<Spotlight>?>(
                onStart = { onStart() },
                onSuccess = { onSuccess(it) },
                onFailure = { onFailure(it) },
                onFinish = { onFinish() }
            )
        }
    }

    private fun <T> getAPIService(): Observable<T>?{
       return when (type){
           APIServiceType.BANNER -> apiService.getBanners() as Observable<T>
           APIServiceType.SPOTLIGHT -> apiService.getSpotlights() as Observable<T>
       }
    }


    private fun <T> callAPI(onStart: () -> Unit, onSuccess: (response: T) -> Unit, onFinish: () -> Unit, onFailure: (error: String?) -> Unit){
        val observable = getAPIService<T>()

        if(observable == null){
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