package teste.exemplo.com.gamecommerce.Presenter.Main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import teste.exemplo.com.gamecommerce.Service.GameService

interface IMainActivityPresenter {
    fun getGamesData(service: GameService)
}