package com.games.ecommerce.main.data.model

import com.google.gson.annotations.SerializedName

data class Banner (
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String
)