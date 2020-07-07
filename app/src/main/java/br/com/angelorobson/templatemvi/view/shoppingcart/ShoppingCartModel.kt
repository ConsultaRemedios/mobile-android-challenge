package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart

data class ShoppingCartModel(
        val shoppingCartResult: ShoppingCartModelResult = ShoppingCartModelResult.Loading()
)

sealed class ShoppingCartModelResult {

    data class Loading(val isLoading: Boolean = true) : ShoppingCartModelResult()

    data class ShoppingCartItemsLoaded(
            val shoppingItemsCart: List<ShoppingCart>,
            val isLoading: Boolean = false,
            val totalWithDiscount: Double,
            val totalWithoutDiscount: Double,
            val itemsSize: Int,
            val freteValue: Double
    ) : ShoppingCartModelResult()

    data class Error(
            val errorMessage: String,
            val isLoading: Boolean = false
    ) : ShoppingCartModelResult()

}