package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.response.RepositoriesResponseDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryGitService {

    @GET("search/repositories")
    fun getAll(@Query("q") q: String = "language:Java", @Query("sort") sort: String = "stars", @Query("page") page: Int): Observable<RepositoriesResponseDto>
}