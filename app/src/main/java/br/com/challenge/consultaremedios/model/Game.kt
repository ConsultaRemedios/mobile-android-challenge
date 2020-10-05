package br.com.challenge.consultaremedios.model

data class Game(
    val id: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val discount: Double,
    val price: Double,
    val description: String,
    val rating: Double,
    val stars: Int,
    val reviews: Int
)