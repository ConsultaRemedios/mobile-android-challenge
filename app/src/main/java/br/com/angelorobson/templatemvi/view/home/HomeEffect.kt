package br.com.angelorobson.templatemvi.view.home

sealed class HomeEffect {

    object ObserverBanner : HomeEffect()
    object ObserverSpotlight : HomeEffect()
}