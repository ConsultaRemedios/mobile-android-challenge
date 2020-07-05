package br.com.angelorobson.templatemvi.view.searchgame

import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class SearchGameEvent

data class InitialEvent(val term: String = "") : SearchGameEvent()

data class GamesFoundEvent(val spotlights: List<Spotlight>,
                           val isLoading: Boolean = false) : SearchGameEvent()

data class SearchGameByTermEvent(val term: String) : SearchGameEvent()

data class SearchGameExceptionEvent(val errorMessage: String) : SearchGameEvent()
