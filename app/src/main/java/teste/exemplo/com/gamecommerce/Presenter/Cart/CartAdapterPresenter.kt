package teste.exemplo.com.gamecommerce.Presenter.Cart

import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.View.Cart.ICartAdapterView

class CartAdapterPresenter(var cartAdapterView: ICartAdapterView) : ICartAdapterPresenter {

    override fun removeCartQuantity(position: Int){
        val currentCartItem = Cart.items[position]
        if(Cart.items[position].qty == 1){
            Cart.items.removeAt(position)
        } else {
            Cart.items[position].qty -= 1
        }
        Cart.totalTax -= 10.0
        Cart.totalGamesPrice -= currentCartItem.game.price
        Cart.totalPrice -= 10.0 + currentCartItem.game.price
        Cart.totalItems -= 1
        cartAdapterView.notifyDataHasChanged()
    }
    override fun addCartQuantity(position: Int){
        Cart.items[position].qty += 1
        Cart.totalTax += 10.0
        Cart.totalGamesPrice += Cart.items[position].game.price
        Cart.totalPrice += 10.0 + Cart.items[position].game.price
        Cart.totalItems += 1
        cartAdapterView.notifyDataHasChanged()
    }
}