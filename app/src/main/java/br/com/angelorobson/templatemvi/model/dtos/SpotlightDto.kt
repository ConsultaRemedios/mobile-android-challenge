package br.com.angelorobson.templatemvi.model.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotlightDto(
        val id: Int,
        val title: String,
        val publisher: String?,
        val image: String?,
        val discount: Double,
        val price: Double,
        val description: String?,
        val rating: Float?,
        val stars: Int?,
        val reviews: Int?
)