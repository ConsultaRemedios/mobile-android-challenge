package br.com.angelorobson.templatemvi.view.repositories

import android.widget.Toast
import br.com.angelorobson.templatemvi.model.repositories.RepositoryGitRepository
import br.com.angelorobson.templatemvi.view.utils.*
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


fun repositoriesUpdate(
        model: RepositoriesModel,
        event: RepositoriesEvent
): Next<RepositoriesModel, RepositoriesEffect> {
    return when (event) {
        is InitialEvent -> dispatch(setOf(ObserveRepositoriesEffect))
        is RepositoriesLoadedEvent -> next(
                model.copy(
                        repositoryResult = RepositoriesResult.RepositoriesLoaded(
                                repositories = event.repositories,
                                isLoading = event.isLoading,
                                hasError = event.hasError,
                                compositeDisposable = event.compositeDisposable
                        )
                )
        )
        is RepositoriesExceptionEvent -> next(
                model.copy(
                        repositoryResult = RepositoriesResult.Error(
                                errorMessage = event.errorMessage
                        )
                )
        )
        is RepositoryClickedEvent -> dispatch(setOf(RepositoryClickedEffect(repository = event.repository)))
        is RetryEvent -> dispatch(setOf(ObserveRetryEffect))
    }
}

class RepositoriesViewModel @Inject constructor(
        repository: RepositoryGitRepository,
        navigator: Navigator,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<RepositoriesModel, RepositoriesEvent, RepositoriesEffect>(
        "RepositoriesViewModel",
        Update(::repositoriesUpdate),
        RepositoriesModel(),
        RxMobius.subtypeEffectHandler<RepositoriesEffect, RepositoriesEvent>()
                .addTransformer(ObserveRepositoriesEffect::class.java) { upstream ->
                    upstream.switchMap {
                        idlingResource.increment()

                        Observable.mergeArray(
                                repository.getAll()
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map {
                                            idlingResource.decrement()

                                            RepositoriesLoadedEvent(
                                                    repositories = it,
                                                    isLoading = false,
                                                    hasError = false,
                                                    compositeDisposable = repository.compositeDisposable
                                            ) as RepositoriesEvent
                                        },
                                repository.loadingSubject
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map {
                                            var hasError = false
                                            if (it.errorMessage.isNotEmpty()) {
                                                hasError = true
                                                activityService.activity.toast(it.errorMessage, Toast.LENGTH_LONG)
                                            }

                                            RepositoriesLoadedEvent(
                                                    repositories = repository.list,
                                                    isLoading = it.isLoading,
                                                    hasError = hasError,
                                                    compositeDisposable = repository.compositeDisposable
                                            ) as RepositoriesEvent
                                        },

                                repository.errorSubject
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .map {
                                            activityService.activity.toast(it.localizedMessage, Toast.LENGTH_LONG)

                                            RepositoriesLoadedEvent(
                                                    repositories = repository.list,
                                                    isLoading = false,
                                                    hasError = true,
                                                    compositeDisposable = repository.compositeDisposable
                                            ) as RepositoriesEvent
                                        }
                        )
                    }
                }
                .addAction(ObserveRetryEffect::class.java) {
                    repository.retry()
                }
                .addConsumer(RepositoryClickedEffect::class.java) { effect ->
                    navigator.to(RepositoriesFragmentDirections.pullRequestFragment(effect.repository, effect.repository.name))
                }
                .build()
)