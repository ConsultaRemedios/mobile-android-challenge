package br.com.angelorobson.templatemvi.view.repositories

import androidx.paging.PagedList
import br.com.angelorobson.templatemvi.model.domains.Repository
import io.reactivex.disposables.CompositeDisposable

data class RepositoriesModel(
        val repositoryResult: RepositoriesResult = RepositoriesResult.RepositoriesLoaded()
)

sealed class RepositoriesResult {
    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : RepositoriesResult()

    data class RepositoriesLoaded(
            val repositories: PagedList<Repository>? = null,
            val isLoading: Boolean = true,
            val hasError: Boolean = false,
            val compositeDisposable: CompositeDisposable? = null
    ) : RepositoriesResult()
}