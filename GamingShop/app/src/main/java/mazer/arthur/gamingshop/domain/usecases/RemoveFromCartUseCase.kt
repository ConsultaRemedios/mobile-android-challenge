package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository

class RemoveFromCartUseCase (private val gamesRepository: GamesRepository) {

    suspend fun removeItem(idGame: Int){
        gamesRepository.removeGameCart(idGame)
    }

}