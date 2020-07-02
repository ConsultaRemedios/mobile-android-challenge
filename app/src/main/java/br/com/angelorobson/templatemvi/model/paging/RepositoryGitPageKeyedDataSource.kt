package br.com.angelorobson.templatemvi.model.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import br.com.angelorobson.templatemvi.model.domains.Repository
import br.com.angelorobson.templatemvi.model.repositories.mapDtoToDomain
import br.com.angelorobson.templatemvi.model.services.RepositoryGitService
import br.com.angelorobson.templatemvi.view.utils.Loading
import br.com.angelorobson.templatemvi.view.utils.Retry
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RepositoryGitPageKeyedDataSource(
        private val api: RepositoryGitService,
        private val loadingSubject: PublishSubject<Loading>,
        private val errorSubject: PublishSubject<Throwable>,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Repository>() {

    var retry: Retry? = null
    var hasError: Boolean = false
    var messageError = ""

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repository>) {
        val numberOfItems = params.requestedLoadSize

        val disposable = api.getAll(page = 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    if (hasError) {
                        loadingSubject.onNext(Loading(true, messageError))
                        return@doOnSubscribe
                    }

                    loadingSubject.onNext(Loading(true))
                }
                .doAfterTerminate {
                    if (hasError) {
                        loadingSubject.onNext(Loading(false, messageError))
                        return@doAfterTerminate
                    }

                    loadingSubject.onNext(Loading(false))
                }
                .subscribe(
                        { response ->
                            hasNoError()
                            Log.d("NGVL", "Loading page: 1")
                            val result = response.items.map {
                                mapDtoToDomain(it)
                            }
                            callback.onResult(result, null, 1)
                        },
                        { t ->
                            hasError(t.localizedMessage)
                            errorSubject.onNext(t)

                            retry = object : Retry {
                                override fun retryCalled() {
                                    hasNoError()
                                    loadInitial(params, callback)

                                }
                            }
                            Log.d("NGVL", "Error loading page: 1", t)
                        }
                )

        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize

        val disposable = api.getAll(page = page)
                .doOnSubscribe {
                    if (hasError) {
                        loadingSubject.onNext(Loading(true, messageError))
                        return@doOnSubscribe
                    }

                    loadingSubject.onNext(Loading(true))
                }
                .doAfterTerminate {
                    if (hasError) {
                        loadingSubject.onNext(Loading(false, messageError))
                        return@doAfterTerminate
                    }

                    loadingSubject.onNext(Loading(false))
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response ->
                            hasNoError()
                            Log.d("NGVL", "Loading page: $page")
                            val result = response.items.map {
                                mapDtoToDomain(it)
                            }
                            callback.onResult(result, page + 1)
                        },
                        { t ->
                            hasError(t.localizedMessage)
                            errorSubject.onNext(t)
                            retry = object : Retry {
                                override fun retryCalled() {
                                    hasNoError()
                                    loadAfter(params, callback)
                                }
                            }

                            Log.d("NGVL", "Error loading page: $page", t)
                        }
                )

        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
//        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun hasNoError() {
        hasError = false
        messageError = ""
    }

    private fun hasError(errorMessage: String) {
        hasError = true
        messageError = errorMessage
    }


}