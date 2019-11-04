package com.example.cheesecakenews.model

data class Game(
    val id: Long,
    val name: String,
    val platform: String,
    val price: Double,
    val image: String
)