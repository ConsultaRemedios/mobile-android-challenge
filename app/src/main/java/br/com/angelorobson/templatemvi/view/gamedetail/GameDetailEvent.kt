package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class GameDetailEvent

data class InitialEvent(val id: Int) : GameDetailEvent()
data class GameLoadedEvent(val spotlight: Spotlight) : GameDetailEvent()
data class GameDetailExceptionEvent(val errorMessage: String) : GameDetailEvent()
