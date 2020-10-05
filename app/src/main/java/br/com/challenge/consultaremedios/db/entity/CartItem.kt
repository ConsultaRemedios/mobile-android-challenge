package br.com.challenge.consultaremedios.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    indices = [Index(value = ["game_id"], unique = true)])
data class CartItem(
    @PrimaryKey(autoGenerate = true)                val id: Int? = null,
    @ColumnInfo(name = "game_id")                   val gameId: Int,
    val title: String,
    @ColumnInfo(name = "box_art_url")               val boxArtUrl: String,
    var quantity: Int,
    @ColumnInfo(name = "unit_price")                val unitPrice: Double,
    @ColumnInfo(name = "unit_price_with_discount")  val unitPriceWithDiscount: Double
)