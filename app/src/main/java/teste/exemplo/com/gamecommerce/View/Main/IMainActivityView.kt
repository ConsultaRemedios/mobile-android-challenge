package teste.exemplo.com.gamecommerce.View.Main

import android.view.View

interface IMainActivityView {

    fun configureToolbar(title: String, hasBackArrow: Boolean)
    fun configureRecyclerView()
    fun configureAdapter()
    fun checkConnectivity()
    fun setLoadingVisibility(visibility: Int)
    fun showLoading()
    fun showTryAgainSnackbar()
    fun updateList()
    fun getToken(): String
    fun goToCart(view: View)
    fun search(view: View)
}