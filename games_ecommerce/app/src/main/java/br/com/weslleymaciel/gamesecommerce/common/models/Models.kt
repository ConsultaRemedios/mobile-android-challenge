package br.com.weslleymaciel.gamesecommerce.common.models

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id") var id : Number,
    @SerializedName("image") var image: String,
    @SerializedName("url") var url: String
)

data class Game(
    @SerializedName("id") var id : Number,
    @SerializedName("description") var description: String?,
    @SerializedName("title") var title: String,
    @SerializedName("publisher") var publisher: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("discount") var discount: Number,
    @SerializedName("price") var price: Number,
    @SerializedName("rating") var rating: Number?,
    @SerializedName("stars") var stars: Number?,
    @SerializedName("reviews") var reviews: Number?
)