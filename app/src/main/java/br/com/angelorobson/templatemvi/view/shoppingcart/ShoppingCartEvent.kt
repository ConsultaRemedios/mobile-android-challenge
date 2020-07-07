package br.com.angelorobson.templatemvi.view.shoppingcart

import br.com.angelorobson.templatemvi.model.domains.ShoppingCart

sealed class ShoppingCartEvent

object InitialEvent : ShoppingCartEvent()
data class RemoveButtonItemClicked(val shoppingCart: ShoppingCart) : ShoppingCartEvent()
data class AddButtonItemClicked(val shoppingCart: ShoppingCart) : ShoppingCartEvent()
data class ClearButtonItemClicked(val shoppingCart: ShoppingCart) : ShoppingCartEvent()
data class ShoppingItemsCartLoadedEvent(val shoppingItemsCart: List<ShoppingCart>,
                                        val isLoading: Boolean = false,
                                        val totalWithDiscount: Double,
                                        val totalWithoutDiscount: Double,
                                        val itemsSize: Int,
                                        val freteValue: Double) : ShoppingCartEvent()

data class ShoppingCartExceptionsEvent(val errorMessage: String) : ShoppingCartEvent()


