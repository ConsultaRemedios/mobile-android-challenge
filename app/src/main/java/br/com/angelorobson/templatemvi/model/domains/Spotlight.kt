package br.com.angelorobson.templatemvi.model.domains

data class Spotlight(
        val id: Int = 0,
        val title: String = "",
        val publisher: String? = "",
        val image: String? = "",
        val discount: Double = 0.0,
        val price: Double = 0.0,
        val description: String? = "",
        val rating: Float? = 0f,
        val stars: Int? = 0,
        val reviews: Int? = 0

)