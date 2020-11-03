package mazer.arthur.gamingshop.utils.listeners

import mazer.arthur.gamingshop.domain.models.GameDetails

interface SpotlightClicked {
    fun onSpotlightClicked(gameDetails: GameDetails)
}