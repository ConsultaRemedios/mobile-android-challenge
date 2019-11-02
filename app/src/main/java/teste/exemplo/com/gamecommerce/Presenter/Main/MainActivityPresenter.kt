package teste.exemplo.com.gamecommerce.Presenter.Main

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import teste.exemplo.com.gamecommerce.View.Main.IMainActivityView
import io.reactivex.internal.disposables.DisposableHelper.isDisposed
import teste.exemplo.com.gamecommerce.Service.GameService
import teste.exemplo.com.gamecommerce.Util.Cache


class MainActivityPresenter(val mainActivityView: IMainActivityView) : IMainActivityPresenter {

    override fun getGamesData(service: GameService) {
        service.getGames("QceNFo1gHd09MJDzyswNqzStlxYGBzUG")
            .doOnError { mainActivityView.showTryAgainSnackbar() }
            .subscribe { response -> Cache.setGames(response)
            mainActivityView.updateList()}
            .isDisposed()
    }

}