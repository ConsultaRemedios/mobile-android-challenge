package br.com.angelorobson.templatemvi.model.utils

import br.com.angelorobson.templatemvi.model.domains.Banner
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.model.dtos.BannerDto
import br.com.angelorobson.templatemvi.model.dtos.PurchaseRequestDto
import br.com.angelorobson.templatemvi.model.dtos.SpotlightDto
import br.com.angelorobson.templatemvi.model.entities.GameEntity
import br.com.angelorobson.templatemvi.model.entities.ShoppingCartEntity

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

fun mapToDto(productsIds: List<Int>, total: Double): PurchaseRequestDto {
    return PurchaseRequestDto(
            items = productsIds,
            total = total
    )
}

fun mapToEntity(shoppingCart: ShoppingCart): ShoppingCartEntity {
    val game = shoppingCart.spotlight
    return ShoppingCartEntity(
            id = if (shoppingCart.id != 0) shoppingCart.id else 0,
            totalWithDiscount = shoppingCart.totalWithDiscount,
            totalWithoutDiscount = shoppingCart.totalWithoutDiscount,
            quantity = shoppingCart.quantity,
            gameEntity = GameEntity(
                    idGame = game.id,
                    discount = game.discount,
                    image = game.image,
                    title = game.title,
                    price = game.price
            )
    )
}

fun mapToDomain(cartEntity: ShoppingCartEntity): ShoppingCart {
    val game = cartEntity.gameEntity
    return ShoppingCart(
            id = cartEntity.id,
            quantity = cartEntity.quantity,
            totalWithDiscount = cartEntity.totalWithDiscount,
            totalWithoutDiscount = cartEntity.totalWithoutDiscount,
            spotlight = Spotlight(
                    id = game.idGame,
                    price = game.price,
                    discount = game.discount,
                    title = game.title,
                    image = game.image,
                    description = "",
                    publisher = "",
                    rating = 0f,
                    reviews = 0,
                    stars = 0
            )
    )

}