package teste.exemplo.com.gamecommerce.View.Game

interface IGameFragmentView {
    fun showLoading()
    fun setLoadingVisibility(visibility: Int)
    fun checkConnectivity()
    fun showTryAgainSnackbar()
    fun getData()
    fun updateGame()
    fun setDescriptionVisibility(visible: Boolean)
}