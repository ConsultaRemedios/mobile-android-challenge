package teste.exemplo.com.gamecommerce.Util

import teste.exemplo.com.gamecommerce.Model.Game

object Cache {
    private val games = ArrayList<Game>()

    fun getGames(): List<Game> {
        return games
    }

    fun setGames(fixtures: List<Game>) {
        Cache.games.clear()
        Cache.games.addAll(fixtures)
    }
}