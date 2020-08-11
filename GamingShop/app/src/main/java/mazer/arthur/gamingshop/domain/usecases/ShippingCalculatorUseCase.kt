package mazer.arthur.gamingshop.domain.usecases

import mazer.arthur.gamingshop.data.GamesRepository

/**
 * Este caso de uso calcula o frete do carrinho.
 * Primeiro é verificado se a soma ultrapassa R$250, caso sim, retorna frete grátis
 * Caso contrário, retorna R$10,00 vezes a quantidade de itens no carrinho
 */
class ShippingCalculatorUseCase(private val gamesRepository: GamesRepository) {

    
    suspend fun getShippingValue(): Int?{
        val totalSumCart = gamesRepository.getTotalDiscountSumCart()
        if (totalSumCart != null && totalSumCart > 250){
            //Frete grátis
            return 0
        }else{
            val totalItemsCart = gamesRepository.getTotalItemsCart()
            if (totalItemsCart != null) {
                return totalItemsCart * 10
            }
        }
        return null
    }

}