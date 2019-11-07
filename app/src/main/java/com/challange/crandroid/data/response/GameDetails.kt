package com.challange.crandroid.data.response

data class GameDetails(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val score: Int,
    val platform: String,
    val image: String,
    val images: List<String>
)