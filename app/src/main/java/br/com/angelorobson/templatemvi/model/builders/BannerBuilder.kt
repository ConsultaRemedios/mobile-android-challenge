package br.com.angelorobson.templatemvi.model.builders

import br.com.angelorobson.templatemvi.model.domains.Banner
import kotlin.random.Random

class BannerBuilder {

    data class Builder(
            var id: Int = 0,
            var image: String = "",
            var url: String = ""
    ) {

        fun id(id: Int) =
                apply { this.id = id }

        fun image(image: String) = apply { this.image = image }
        fun url(url: String) = apply { this.url = url }

        fun oneBanner() = apply {
            id = Random(50).nextInt()
            image = "https://image.freepik.com/free-vector/simple-unique-gaming-banner-template_92741-92.jpg"
            url = "https://consultaremedios.com.br/"
        }

        fun build() = Banner(
                id = id,
                image = image,
                url = url
        )
    }
}