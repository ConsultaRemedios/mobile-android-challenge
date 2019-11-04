package com.example.cheesecakenews.model

data class GameItem(
    val id: Long,
    val name: String,
    val description:String,
    val price: Double,
    val score: Int,
    val platform: String,
    val image: String,
    val images: List<String>
)