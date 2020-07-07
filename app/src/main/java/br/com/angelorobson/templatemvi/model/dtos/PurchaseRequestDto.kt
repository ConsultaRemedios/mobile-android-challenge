package br.com.angelorobson.templatemvi.model.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseRequestDto(
        val items: List<Int>,
        val total: Double
)