package com.games.ecommerce.main.data.model


import java.io.Serializable

data class GameRepositoryResponse(
    val id: Int,
    val title: String,
    val image: String,
    val price: Double,
    val originalPrice: Double,
    val description: String,
    val rating: Double,
    val stars: Int,
    val publisher: String,
    val reviews: Int
) : Serializable