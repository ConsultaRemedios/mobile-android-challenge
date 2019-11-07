package com.challange.crandroid.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Game(
    val id: Int,
    @SerializedName("name")
    val title: String,
    val price: Double,
    val platform: String,
    val image: String
) : Serializable