package teste.exemplo.com.gamecommerce.Presenter.Cart

interface ICartAdapterPresenter {
    fun removeCartQuantity(position: Int)
    fun addCartQuantity(position: Int)
}