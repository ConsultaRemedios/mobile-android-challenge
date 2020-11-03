package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.CartQuantItemsListener

/**
 * Este caso de uso retorna a quantidade de itens no carrinho pra serem mostradas no home.
 * Caso algum o usuário tenha algum item no carrinho, dispara o listener hasItems(numItems)
 * Caso o item não tenha nenhum item dispara o listener emptyCart()
 */
class GetQuantItemCartUseCase(private val gamesRepository: GamesRepository, private var listener: CartQuantItemsListener) {

    suspend fun getNumItems(){
        val quantItems = gamesRepository.getTotalItemsCart()
        if (quantItems != null && quantItems > 0){
            listener.hasItems(quantItems)
        }else{
            listener.emptyCart()
        }
    }
}