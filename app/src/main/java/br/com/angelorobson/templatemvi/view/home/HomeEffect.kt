package br.com.angelorobson.templatemvi.view.home

import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class HomeEffect {

    object ObserverBanner : HomeEffect()
    object ObserverSpotlight : HomeEffect()
    data class GameClickedEffect(val spotlight: Spotlight) : HomeEffect()
    object SearchViewClickedEffect : HomeEffect()
    object CartActionButtonClickedEffect : HomeEffect()
    data class BannerClickedEffect(val url: String) : HomeEffect()
}