package com.games.ecommerce.main.data.model


import java.io.Serializable

data class GameRepositoryResponse(
    val id: Int,
    val title: String,
    val image: String = "",
    val price: Double,
    val originalPrice: Double = 0.0,
    val description: String = "",
    val rating: Double = 0.0,
    val stars: Int = 0,
    val publisher: String = "",
    val reviews: Int = 0
) : Serializable