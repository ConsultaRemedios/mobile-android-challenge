package com.challange.crandroid.data.response

import com.google.gson.annotations.SerializedName

data class Game(
    val id: Int,
    @SerializedName("name")
    val title: String,
    val price: Double,
    val platform: String,
    val image: String
)