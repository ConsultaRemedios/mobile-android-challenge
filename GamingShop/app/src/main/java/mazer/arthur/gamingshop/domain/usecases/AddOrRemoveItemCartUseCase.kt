package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.data.remote.entities.Cart
import mazer.arthur.gamingshop.utils.listeners.CartChangedListener


/**
 * Este caso de uso adiciona ou remove o item do carrinho. Representa o evento ao clicar no botão carrinho na tela de detalhes do jogo.
 * Caso o item já esteja adicionado no banco de dados, o item é removido e então dispara o listener itemRemoved()
 * Caso o item não seja encontrado no banco, ele é adicionado e então dispara o listener itemAdded()
 */
class AddOrRemoveItemCartUseCase(private val gamesRepository: GamesRepository, private var listener: CartChangedListener) {

    suspend fun addOrRemove(cart: Cart){

        val id = gamesRepository.getItemCartById(cart.idGameDetails)
        if (id == null){
            //se o id é null, o item ainda não se encontra no banco de dados
            val addDb = gamesRepository.addGameCart(cart)
            if (addDb > 0) {
                listener.itemAdded()
            }else{
                listener.error()
            }
        }else{
            //id não nulo, item já se encontra no banco de dados
            gamesRepository.removeGameCart(id)
            listener.itemRemoved()
        }
    }

}