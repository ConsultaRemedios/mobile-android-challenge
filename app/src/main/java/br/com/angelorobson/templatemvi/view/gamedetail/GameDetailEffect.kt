package br.com.angelorobson.templatemvi.view.gamedetail

import br.com.angelorobson.templatemvi.model.domains.Spotlight

sealed class GameDetailEffect

data class ObservableGameEffect(val id: Int) : GameDetailEffect()
data class AddOrRemoveItemCardEffect(val spotlight: Spotlight?) : GameDetailEffect()
data class GetItemCartEffect(val spotlight: Spotlight?) : GameDetailEffect()
