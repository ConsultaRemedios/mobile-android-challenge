package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.SpotlightDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotlightService {

    @GET("spotlight")
    fun getAll(): Observable<List<SpotlightDto>>

    @GET("games/{id}")
    fun getGame(@Path("id") id: Int): Single<SpotlightDto>

    @GET("games/search")
    fun searchGameByTerm(@Query("term") term: String): Observable<List<SpotlightDto>>
}