package mazer.arthur.gamingshop.domain.usecases

import kotlinx.coroutines.*
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.CheckoutListener

class CheckoutUseCase(private val gamesRepository: GamesRepository, private val listener: CheckoutListener) {

    private var courouTineJob: Job? = null

    suspend fun checkout(){
        val quantItemsCart = gamesRepository.getTotalItemsCart()
        if (quantItemsCart == null || quantItemsCart == 0){
            listener.cartIsEmpty()
        }else {
            courouTineJob = CoroutineScope(Dispatchers.IO).launch {
                val response = gamesRepository.checkout()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        listener.checkoutSuccessful()
                    } else {
                        listener.checkoutFailure()
                    }
                }
            }
        }
    }
}