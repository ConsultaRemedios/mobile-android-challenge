package mazer.arthur.gamingshop.domain.usecases

import kotlinx.coroutines.*
import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.CheckoutListener

class CheckoutUseCase(private val gamesRepository: GamesRepository, private val listener: CheckoutListener) {

    private var courouTineJob: Job? = null

    fun checkout(){
        courouTineJob = CoroutineScope(Dispatchers.IO).launch {
            val response = gamesRepository.checkout()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    listener.checkoutSuccessful()
                }else{
                    listener.checkoutFailure()
                }
            }
        }
    }
}