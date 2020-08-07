package br.com.weslleymaciel.gamesecommerce.data.repository

import br.com.weslleymaciel.gamesecommerce.common.models.Banner
import br.com.weslleymaciel.gamesecommerce.common.models.Game
import br.com.weslleymaciel.gamesecommerce.data.WebServiceFactory
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody

class GamesRepository {
    private val service = WebServiceFactory.create()

    fun getBanners(observer: SingleObserver<List<Banner>>) {
        service.getBanners()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data?.let {
                    observer.onSuccess(it)
                } ?: run {
                    observer.onSuccess(listOf())
                }
            }, { error ->
                observer.onError(error)
            })
    }

    fun getSpotlight(observer: SingleObserver<List<Game>>) {
        service.getSpotlight()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data?.let {
                    observer.onSuccess(it)
                } ?: run {
                    observer.onSuccess(listOf())
                }
            }, { error ->
                observer.onError(error)
            })
    }

    fun getGameDetail(id: Number, observer: SingleObserver<Game>) {
        service.getGameDetail(id)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data?.let {
                    observer.onSuccess(it)
                }
            }, { error ->
                observer.onError(error)
            })
    }

    fun searchGame(term: String, observer: SingleObserver<List<Game>>) {
        service.searchGame(term)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data?.let {
                    observer.onSuccess(it)
                } ?: run {
                    observer.onSuccess(listOf())
                }
            }, { error ->
                observer.onError(error)
            })
    }

    fun checkout(observer: SingleObserver<ResponseBody>) {
        service.checkout()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data?.let {
                    observer.onSuccess(it)
                }
            }, { error ->
                observer.onError(error)
            })
    }
}