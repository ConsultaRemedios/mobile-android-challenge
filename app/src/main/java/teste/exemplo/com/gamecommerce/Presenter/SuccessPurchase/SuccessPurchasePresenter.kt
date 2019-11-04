package teste.exemplo.com.gamecommerce.Presenter.SuccessPurchase

import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.View.SuccessPurchase.ISuccessPurchaseFragmentView

class SuccessPurchasePresenter(var successPurchaseView: ISuccessPurchaseFragmentView) : ISuccessPurchasePresenter {

    override fun eraseCart(){
        Cart.items = ArrayList()
        Cart.totalTax = 0.0
        Cart.totalPrice = 0.0
        Cart.totalItems = 0
        successPurchaseView.updateViews()
    }
}