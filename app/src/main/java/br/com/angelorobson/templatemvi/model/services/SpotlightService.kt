package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.SpotlightDto
import io.reactivex.Observable
import retrofit2.http.GET

interface SpotlightService {

    @GET("spotlight")
    fun getAll(): Observable<List<SpotlightDto>>
}