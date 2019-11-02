package teste.exemplo.com.gamecommerce.Presenter.Game

import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.View.Game.IGameFragmentView

class GamePresenter(var gameView: IGameFragmentView) : IGamePresenter {
    var service: GameService = GameService()
    override fun getGameDataById() {
        service.getGameById(Cache.getSelectedGameId(),"QceNFo1gHd09MJDzyswNqzStlxYGBzUG")
            .doOnError { gameView.showTryAgainSnackbar() }
            .subscribe { response -> Cache.setGame(response)
                gameView.updateGame()}
            .isDisposed()
    }

}