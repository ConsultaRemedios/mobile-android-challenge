package com.games.ecommerce.main.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GameServiceResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discount") val discount: Double,
    @SerializedName("description") val description: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stars") val stars: Int,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("reviews") val reviews: Int
) : Serializable


fun GameServiceResponse.toGameRepositoryResponse(): GameRepositoryResponse {
    return GameRepositoryResponse(
        id = this.id,
        title = this.title,
        image = this.image?: "",
        price = this.price - this.discount,
        originalPrice = this.price,
        description = this.description?: "",
        rating = this.rating?: 0.0,
        stars = this.stars?: 0,
        publisher = this.publisher?: "",
        reviews = this.reviews?: 0
    )
}

