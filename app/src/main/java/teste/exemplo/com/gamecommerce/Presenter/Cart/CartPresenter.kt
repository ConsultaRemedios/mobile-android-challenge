package teste.exemplo.com.gamecommerce.Presenter.Cart

import android.util.Log
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import teste.exemplo.com.gamecommerce.View.Cart.ICartFragmentView

class CartPresenter(var cartView: ICartFragmentView) : ICartPresenter {

    var service: GameService = GameService()

    override fun getData(){
        cartView.updateToolbar()
        if(Cart.totalGamesPrice > 250.0){
            cartView.updateTotalPrice(formatMoney(Cart.totalGamesPrice))
            cartView.updateDeliveryTax(formatMoney(0.0))
        } else {
            cartView.updateTotalPrice(formatMoney(Cart.totalPrice))
            cartView.updateDeliveryTax(formatMoney(Cart.totalTax))
        }
        cartView.updatePaymentData()
        cartView.updateAddress()
        cartView.setOnClickListeners()
    }

    override fun finishPurchase(){
        if(Cart.items.size <= 0)
            cartView.showEmptyCartToast()
        service.checkout(cartView.getToken())
            .doOnError { cartView.showTryAgainSnackbar() }
            .subscribe {    response -> Log.d("RESPOSTA", response.toString())
                cartView.goToSuccessPurchaseScreen() }
            .isDisposed
    }
}