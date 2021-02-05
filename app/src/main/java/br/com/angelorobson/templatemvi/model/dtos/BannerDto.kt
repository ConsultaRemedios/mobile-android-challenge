package br.com.angelorobson.templatemvi.model.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BannerDto(
        val id: Int,
        val image: String,
        val url: String
)