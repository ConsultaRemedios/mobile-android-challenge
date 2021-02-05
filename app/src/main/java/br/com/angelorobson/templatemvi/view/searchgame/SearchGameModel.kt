package br.com.angelorobson.templatemvi.view.searchgame

import br.com.angelorobson.templatemvi.model.domains.Spotlight

data class SearchGameModel(
        val searchGameResult: SearchGameResult = SearchGameResult.Loading()
)

sealed class SearchGameResult {

    data class Loading(val isLoading: Boolean = true) : SearchGameResult()

    data class GamesFoundByTerm(
            val spotlights: List<Spotlight>,
            val isLoading: Boolean = false
    ) : SearchGameResult()

    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : SearchGameResult()

}