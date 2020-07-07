package br.com.angelorobson.templatemvi.model.repositories

import br.com.angelorobson.templatemvi.model.dtos.PurchaseRequestDto
import br.com.angelorobson.templatemvi.model.services.PurchaseService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PurchaseRepository @Inject constructor(
        private val purchaseService: PurchaseService
) {

    fun checkout(productsIds: List<Int>, total: Double): Completable {
        return Single.fromCallable { mapToDto(productsIds, total) }
                .flatMapCompletable { purchaseRequest ->
                    purchaseService.checkout(purchaseRequest)
                }
    }

}

fun mapToDto(productsIds: List<Int>, total: Double): PurchaseRequestDto {
    return PurchaseRequestDto(
            items = productsIds,
            total = total
    )
}