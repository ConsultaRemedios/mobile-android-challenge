package br.com.angelorobson.templatemvi.model.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCartEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val totalWithDiscount: Double,
        val totalWithoutDiscount: Double,
        val quantity: Int,
        @Embedded val gameEntity: GameEntity
)