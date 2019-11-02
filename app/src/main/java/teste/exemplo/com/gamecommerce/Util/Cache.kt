package teste.exemplo.com.gamecommerce.Util

import teste.exemplo.com.gamecommerce.Model.Game

object Cache {
    private val games = ArrayList<Game>()
    private var selectedGameId = 0
    private var game = Game()

    fun getGames(): List<Game> {
        return games
    }

    fun setGames(fixtures: List<Game>) {
        Cache.games.clear()
        Cache.games.addAll(fixtures)
    }

    fun getSelectedGameId(): Int{
        return selectedGameId
    }

    fun setSelectedGameId(selectedGameId: Int){
        Cache.selectedGameId = selectedGameId
    }

    fun getGame(): Game {
        return game
    }

    fun setGame(game: Game){
        Cache.game = game
    }
}