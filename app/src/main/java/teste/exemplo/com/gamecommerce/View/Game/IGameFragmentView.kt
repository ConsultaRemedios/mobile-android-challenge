package teste.exemplo.com.gamecommerce.View.Game

import android.view.View

interface IGameFragmentView {
    fun showLoading()
    fun setLoadingVisibility(visibility: Int)
    fun showTryAgainSnackbar()
    fun getData()
    fun updateGame(game_price: String, delivery_value: String)
    fun setDescriptionVisibility(visible: Boolean)
    fun goToCartFragment()
    fun getToken(): String
}