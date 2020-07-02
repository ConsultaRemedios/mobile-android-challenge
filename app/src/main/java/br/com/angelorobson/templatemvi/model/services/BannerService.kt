package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.BannerDto
import io.reactivex.Observable
import retrofit2.http.GET

interface BannerService {

    @GET("banners")
    fun getAll(): Observable<List<BannerDto>>
}