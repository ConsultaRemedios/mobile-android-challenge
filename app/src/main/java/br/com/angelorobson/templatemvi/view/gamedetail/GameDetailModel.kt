package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.domains.Spotlight

data class GameDetailModel(
        val gameDetailResult: GameDetailResult = GameDetailResult.Loading()
)

sealed class GameDetailResult {

    data class Loading(val isLoading: Boolean = true) : GameDetailResult()

    data class GameLoaded(
            val spotlight: Spotlight,
            val isLoading: Boolean = false
    ) : GameDetailResult()

    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : GameDetailResult()

}