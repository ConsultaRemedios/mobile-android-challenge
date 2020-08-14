package mazer.arthur.gamingshop.utils.listeners

import mazer.arthur.gamingshop.domain.models.GameDetails

interface OnGameSearched {
    fun onGameSearched(gameList: List<GameDetails>)
}