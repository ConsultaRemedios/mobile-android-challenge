package br.com.angelorobson.templatemvi.model.repositories

import br.com.angelorobson.templatemvi.model.domains.Banner
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.model.dtos.BannerDto
import br.com.angelorobson.templatemvi.model.dtos.SpotlightDto
import br.com.angelorobson.templatemvi.model.services.BannerService
import br.com.angelorobson.templatemvi.model.services.SpotlightService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class HomeServiceRepository @Inject constructor(
        private val bannerService: BannerService,
        private val spotlightService: SpotlightService
) {

    fun getAll(): Observable<List<Banner>> {
        return bannerService.getAll()
                .map { response ->
                    response.map {
                        mapDtoToDomain(it)
                    }
                }
    }

    fun getGame(id: Int): Single<Spotlight> {
        return spotlightService.getGame(id)
                .map {
                    mapDtoToDomain(it)
                }
    }

    fun searchByTerm(term: String): Observable<List<Spotlight>> {
        return spotlightService.searchGameByTerm(term)
                .map { response ->
                    response.map {
                        mapDtoToDomain(it)
                    }
                }
    }

    fun getAllSpotlights(): Observable<List<Spotlight>> {
        return spotlightService.getAll()
                .map { response ->
                    response.map {
                        mapDtoToDomain(it)
                    }
                }
    }
}

fun mapDtoToDomain(dto: BannerDto): Banner {
    return Banner(
            id = dto.id,
            image = dto.image,
            url = dto.url
    )
}

fun mapDtoToDomain(dto: SpotlightDto): Spotlight {
    return Spotlight(
            id = dto.id,
            image = dto.image,
            description = dto.description,
            title = dto.title,
            discount = dto.discount,
            price = dto.price,
            publisher = dto.publisher,
            rating = dto.rating,
            reviews = dto.reviews,
            stars = dto.stars
    )
}