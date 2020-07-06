package br.com.angelorobson.templatemvi.model.entities

import androidx.room.Entity

@Entity
data class GameEntity(
        val idGame: Int,
        val title: String,
        val image: String?,
        val discount: Double,
        val price: Double
)