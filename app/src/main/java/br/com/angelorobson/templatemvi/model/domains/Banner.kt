package br.com.angelorobson.templatemvi.model.domains

import com.squareup.moshi.JsonClass

data class Banner(
        val id: Int,
        val image: String,
        val url: String
)