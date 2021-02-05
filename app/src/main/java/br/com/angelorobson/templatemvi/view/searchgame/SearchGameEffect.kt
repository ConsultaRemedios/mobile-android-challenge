package br.com.angelorobson.templatemvi.view.searchgame

import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class SearchGameEffect {

    data class ObserverSpotlightByTermEffect(val term: String) : SearchGameEffect()
    data class GameClickedEffect(val spotlight: Spotlight) : SearchGameEffect()
}