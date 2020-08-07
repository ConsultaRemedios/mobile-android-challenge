package mazer.arthur.gamingshop.view.listeners

import mazer.arthur.gamingshop.models.GameDetails

interface SpotlightClicked {
    fun onSpotlightClicked(gameDetails: GameDetails)
}