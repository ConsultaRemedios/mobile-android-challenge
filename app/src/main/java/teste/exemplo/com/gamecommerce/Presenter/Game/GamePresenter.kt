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
        val gameAlreadyAdded = Cart.items.find { it.game.id == game.id }
        if(gameAlreadyAdded == null) {
            Cart.items.add(GameAddedToCart(game, 1))
        } else {
            Cart.items.remove(gameAlreadyAdded)
            gameAlreadyAdded.qty += 1
            Cart.items.add(gameAlreadyAdded)
        }
        Cart.totalItems += 1
        Cart.totalTax += 10.0
        Cart.totalPrice += 10.0 + game.price
        Cart.totalGamesPrice += game.price
        gameView.goToCartFragment()
    }

}