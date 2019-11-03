package teste.exemplo.com.gamecommerce.Presenter.Cart

import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import teste.exemplo.com.gamecommerce.View.Cart.ICartFragmentView

class CartPresenter(var cartView: ICartFragmentView) : ICartPresenter {

    var service: GameService = GameService()

    override fun getData(){
        cartView.updateToolbar()
        cartView.updateTotalPrice(formatMoney(Cart.totalPrice))
        cartView.updateDeliveryTax(formatMoney(Cart.totalTax))
        cartView.updatePaymentData()
        cartView.updateAddress()
    }

    override fun finishPurchase(){
        service.checkout(cartView.getToken())
            .doOnError { cartView.showTryAgainSnackbar() }
            .subscribe { cartView.goToSuccessPurchaseScreen() }
            .dispose()
    }
}