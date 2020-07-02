package br.com.angelorobson.templatemvi.view.home

import br.com.angelorobson.templatemvi.model.domains.Banner
import br.com.angelorobson.templatemvi.model.domains.Spotlight

data class HomeModel(
        val homeResult: HomeResult = HomeResult.Loading()
)

sealed class HomeResult {

    data class Loading(val isLoading: Boolean = true) : HomeResult()

    data class BannerLoaded(
            val banners: List<Banner>,
            val isLoading: Boolean = false
    ) : HomeResult()

    data class SpotlightsLoaded(
            val spotlights: List<Spotlight>,
            val isLoading: Boolean = false
    ) : HomeResult()

    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : HomeResult()

}