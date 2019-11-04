package teste.exemplo.com.gamecommerce.View.Cart

interface ICartAdapterView {
    fun updateHolderViews(position: Int)
    fun setHolderTexts()
    fun setHolderImage()
    fun setHolderClickListeners(position: Int)
    fun updateCartQuantity(position: Int)
    fun notifyDataHasChanged()
}