package teste.exemplo.com.gamecommerce.View.Cart

interface ICartFragmentView {
    fun configureRecyclerView()
    fun configureAdapter()
    fun updateToolbar()
    fun updateTotalPrice(totalPrice: String)
    fun updateDeliveryTax(delivery_tax:String)
    fun updatePaymentData()
    fun updateAddress()
    fun setOnClickListeners()
    fun goToSuccessPurchaseScreen()
    fun showTryAgainSnackbar()
    fun getToken(): String
}