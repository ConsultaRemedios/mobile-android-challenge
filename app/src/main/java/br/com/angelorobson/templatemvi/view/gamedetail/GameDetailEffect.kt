package br.com.angelorobson.templatemvi.view.gamedetail

sealed class GameDetailEffect

data class ObservableGame(val id: Int) : GameDetailEffect()