package teste.exemplo.com.gamecommerce.Service

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import teste.exemplo.com.gamecommerce.Model.Game
import teste.exemplo.com.gamecommerce.Util.RestUtil


class GameService {

    @Synchronized
    fun getGameById(id: Int?, token: String): Observable<Game> {
        return RestUtil.api.getGameById(id, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Synchronized
    fun getGames(token: String): Observable<List<Game>> {
        return RestUtil.api.getGames(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Synchronized
    fun checkout(token: String): Observable<Response<Void>> {
        return RestUtil.api.checkout(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}