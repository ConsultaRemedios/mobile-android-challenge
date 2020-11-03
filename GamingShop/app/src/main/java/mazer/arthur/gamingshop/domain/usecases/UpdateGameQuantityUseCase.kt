package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository

class UpdateGameQuantityUseCase(private val gamesRepository: GamesRepository) {

    suspend fun updateQuantity(id: Int, quantity: Int){
        gamesRepository.updateGameQuanitty(id, quantity)
    }
}