package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.OnGameSearched

class SearchGameUseCase(private val gamesRepository: GamesRepository, private val listener: OnGameSearched) {

    suspend fun search(term: String){
        val result = gamesRepository.search(term)
        listener.onGameSearched(result)
    }
}