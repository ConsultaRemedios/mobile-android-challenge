package teste.exemplo.com.gamecommerce.View.Cart

interface ICartFragmentView {
    fun configureRecyclerView()
    fun configureAdapter()
    fun updateToolbar()
    fun updatePrices(totalPrice: String, delivery_tax:String)
    fun updatePaymentData()
    fun updateAddress()
    fun setOnClickListeners()
    fun goToSuccessPurchaseScreen()
    fun showTryAgainSnackbar()
    fun getToken(): String
}