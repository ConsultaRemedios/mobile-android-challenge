package com.challange.crandroid.data

import com.challange.crandroid.data.response.Game

data class CartItem(
    val game: Game,
    var quantidade: Int,
    var precoSomaQuantidade: Double
)