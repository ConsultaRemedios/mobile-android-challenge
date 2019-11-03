package teste.exemplo.com.gamecommerce.Presenter.Cart

import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import teste.exemplo.com.gamecommerce.View.Cart.ICartFragmentView

class CartPresenter(var cartView: ICartFragmentView) : ICartPresenter {

    var service: GameService = GameService()

    override fun getData(){
        cartView.updateToolbar()
        cartView.updatePrices(formatMoney(Cart.totalPrice + Cart.totalTax),
            formatMoney(Cart.totalTax))
        cartView.updatePaymentData()
        cartView.updateAddress()
    }

    override fun finishPurchase(){
        service.checkout(cartView.getToken())
            .doOnError { cartView.showTryAgainSnackbar() }
            .subscribe { response ->
                cartView.goToSuccessPurchaseScreen()
            }
            .isDisposed()
    }
}