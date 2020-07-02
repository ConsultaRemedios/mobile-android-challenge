package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.response.PullRequestsDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PullRequestService {

    @GET("repos/{owner}/{repository_name}/pulls")
    fun getAll(@Path("owner") owner: String, @Path("repository_name") repositoryName: String): Observable<List<PullRequestsDto>>
}