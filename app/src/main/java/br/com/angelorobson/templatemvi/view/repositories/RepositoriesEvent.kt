package br.com.angelorobson.templatemvi.view.repositories

import androidx.paging.PagedList
import br.com.angelorobson.templatemvi.model.domains.Repository
import io.reactivex.disposables.CompositeDisposable

sealed class RepositoriesEvent

object InitialEvent : RepositoriesEvent()
object RetryEvent : RepositoriesEvent()

data class RepositoriesLoadedEvent(val repositories: PagedList<Repository>,
                                   val isLoading: Boolean = true,
                                   val hasError: Boolean = false,
                                   val compositeDisposable: CompositeDisposable) : RepositoriesEvent()

data class RepositoriesExceptionEvent(val errorMessage: String) : RepositoriesEvent()

data class RepositoryClickedEvent(val repository: Repository) : RepositoriesEvent()