package teste.exemplo.com.gamecommerce.View.Main

interface IMainActivityView {

    fun configureRecyclerView()
    fun setAdapter()
    fun checkConnectivity()
    fun setLoadingVisibility(visibility: Int)
    fun showLoading()
    fun showTryAgainSnackbar()
    fun updateList()
}