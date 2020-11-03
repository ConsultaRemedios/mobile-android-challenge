package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.CheckoutListener

class CheckoutUseCase(private val gamesRepository: GamesRepository, private val listener: CheckoutListener) {

    suspend fun checkout(){
        val quantItemsCart = gamesRepository.getTotalItemsCart()
        if (quantItemsCart == null || quantItemsCart == 0){
            listener.cartIsEmpty()
        }else {
            val response = gamesRepository.checkout()

            if (response.isSuccessful) {
                listener.checkoutSuccessful()
            } else {
                listener.checkoutFailure()
            }
        }
    }
}