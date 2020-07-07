package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart

sealed class ShoppingCartEffect {

    object ObserverShoppingCart : ShoppingCartEffect()
    data class AddButtonItemClickedEffect(val shoppingCart: ShoppingCart) : ShoppingCartEffect()
    data class RemoveButtonItemClickedEffect(val shoppingCart: ShoppingCart) : ShoppingCartEffect()
    data class ClearButtonItemClickedEffect(val shoppingCart: ShoppingCart) : ShoppingCartEffect()
    data class ButtonPurchaseEffect(val shoppingItemsCart: List<ShoppingCart>, val total: Double) : ShoppingCartEffect()
    object ClearDatabaseEffect : ShoppingCartEffect()

}