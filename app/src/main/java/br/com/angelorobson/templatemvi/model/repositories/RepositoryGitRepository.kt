package br.com.angelorobson.templatemvi.model.repositories

import androidx.paging.PagedList
import br.com.angelorobson.templatemvi.model.domains.Repository
import br.com.angelorobson.templatemvi.model.domains.User
import br.com.angelorobson.templatemvi.model.dtos.response.RepositoryDto
import br.com.angelorobson.templatemvi.model.paging.RepositoryGitPageKeyedDataSource
import br.com.angelorobson.templatemvi.model.services.RepositoryGitService
import br.com.angelorobson.templatemvi.view.utils.Loading
import br.com.angelorobson.templatemvi.view.utils.MainThreadExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class RepositoryGitRepository @Inject constructor(
        repositoryGitService: RepositoryGitService
) {

    var list: PagedList<Repository>
    var loadingSubject = PublishSubject.create<Loading>()
    var errorSubject = PublishSubject.create<Throwable>()

    val compositeDisposable = CompositeDisposable()
    private val datasource = RepositoryGitPageKeyedDataSource(repositoryGitService, loadingSubject, errorSubject, compositeDisposable)

    init {
        val executor = MainThreadExecutor()

        val config: PagedList.Config = PagedList.Config.Builder()
                .setPageSize(25)
                .setInitialLoadSizeHint(25 * 2)
                .setPrefetchDistance(15)
                .setEnablePlaceholders(true)
                .build()


        list = PagedList.Builder(datasource, config)
                .setFetchExecutor(executor)
                .setNotifyExecutor(executor)
                .build()
    }


    fun getAll(): Observable<PagedList<Repository>> {
        return Observable.just(list)
    }

    fun retry() {
        datasource.retry?.retryCalled()
    }
}

fun mapDtoToDomain(dto: RepositoryDto): Repository {
    return Repository(
            id = dto.id,
            name = dto.name,
            user = User(
                    login = dto.owner.login,
                    avatarUrl = dto.owner.avatar_url
            ),
            description = dto.description ?: "",
            forksCount = dto.forks_count,
            stargazersCount = dto.stargazers_count
    )
}

