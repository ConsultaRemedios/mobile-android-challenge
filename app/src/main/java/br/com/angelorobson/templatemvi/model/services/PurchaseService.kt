package br.com.angelorobson.templatemvi.model.services

import br.com.angelorobson.templatemvi.model.dtos.PurchaseRequestDto
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseService {

    @POST("checkout")
    fun checkout(@Body productsIds: PurchaseRequestDto): Completable
}