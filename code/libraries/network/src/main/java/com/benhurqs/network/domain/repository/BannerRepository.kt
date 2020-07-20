package com.benhurqs.network.domain.repository

import com.benhurqs.network.data.ChallengeService
import com.benhurqs.network.entities.Banner
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BannerRepository(
    private val apiService: ChallengeService = ChallengeService(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()
) {

    companion object{
        private var mInstance: BannerRepository? = null

        @Synchronized
        fun getInstance(): BannerRepository {
            if (mInstance == null) {
                mInstance = BannerRepository()
            }
            return mInstance!!
        }
    }

    open fun callBannerAPI( onStart: () -> Unit, onSuccess: (list: List<Banner>?) -> Unit, onFinish: () -> Unit, onFailure: (error: String?) -> Unit){
        apiService.getBanners()
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .doOnSubscribe {
                onStart()
            }
            .subscribe(object : Observer<List<Banner>?>{
                override fun onComplete() {
                    onFinish()
                }

                override fun onSubscribe(d: Disposable?) {}

                override fun onNext(bannerList: List<Banner>?) {
                    onSuccess(bannerList)
                }

                override fun onError(e: Throwable?) {
                    onFailure(e?.message)
                }
            })
    }

}