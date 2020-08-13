package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository
import mazer.arthur.gamingshop.utils.listeners.CartChangedListener
import mazer.arthur.gamingshop.utils.listeners.ShippingChangedListener

/**
 * Este caso de uso calcula o frete do carrinho.
 * Primeiro é verificado se a soma ultrapassa R$250, caso sim, retorna frete grátis
 * Caso contrário, retorna R$10,00 vezes a quantidade de itens no carrinho
 */
class ShippingCalculatorUseCase(private val gamesRepository: GamesRepository, private var listener: ShippingChangedListener) {

    suspend fun getShippingValue(){
        val totalSumCart = gamesRepository.getTotalDiscountSumCart()
        if (totalSumCart != null && totalSumCart > 250){
            //Frete grátis
            listener.onShippingValueChanged(0)
        }else{
            val totalItemsCart = gamesRepository.getTotalItemsCart()
            if (totalItemsCart != null){
                listener.onShippingValueChanged(totalItemsCart * 10)
            }else{
                //Não encontrado items no carrinho
                listener.emptyCart()
            }
        }
    }

}