package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart

sealed class ShoppingCartEvent

object InitialEvent : ShoppingCartEvent()
data class ShoppingItemsCartLoadedEvent(val shoppingItemsCart: List<ShoppingCart>, val isLoading: Boolean = false) : ShoppingCartEvent()
data class ShoppingCartExceptionsEvent(val errorMessage: String) : ShoppingCartEvent()


