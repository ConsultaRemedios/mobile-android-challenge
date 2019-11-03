package teste.exemplo.com.gamecommerce.Presenter.Main

import teste.exemplo.com.gamecommerce.View.Main.IMainActivityView
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.Cache


class MainActivityPresenter(val mainActivityView: IMainActivityView) : IMainActivityPresenter {
    var service: GameService = GameService()

    override fun getGamesData() {
        service.getGames(mainActivityView.getToken())
            .doOnError { mainActivityView.showTryAgainSnackbar() }
            .subscribe { response -> Cache.setGames(response)
            mainActivityView.updateList()}
            .isDisposed
    }

}