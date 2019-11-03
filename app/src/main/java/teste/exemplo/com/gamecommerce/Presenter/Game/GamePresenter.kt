package teste.exemplo.com.gamecommerce.Presenter.Game

import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.Model.GameAddedToCart
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.MoneyUtil.formatMoney
import teste.exemplo.com.gamecommerce.View.Game.IGameFragmentView

class GamePresenter(var gameView: IGameFragmentView) : IGamePresenter {

    var service: GameService = GameService()
    override fun getGameDataById() {
        service.getGameById(Cache.getSelectedGameId(),gameView.getToken())
            .doOnError { gameView.showTryAgainSnackbar() }
            .subscribe { response ->
                val game = response as Game
                Cache.setGame(game)
                gameView.updateGame(formatMoney(game.price), formatMoney(10.0))}
            .isDisposed()
    }

    override fun addGameToCart(){
        val game = Cache.getGame()
        Cart.items.add(GameAddedToCart(game, 1))
        Cart.totalItems += 1
        Cart.totalPrice += game.price
        Cart.totalTax += 10.0
        gameView.goToCartFragment()
    }

}