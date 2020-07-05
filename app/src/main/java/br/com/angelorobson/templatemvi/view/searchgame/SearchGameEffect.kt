package br.com.angelorobson.templatemvi.view.searchgame

sealed class SearchGameEffect {

    data class ObserverSpotlightByTerm(val term: String) : SearchGameEffect()
}