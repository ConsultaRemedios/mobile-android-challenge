package com.example.cheesecakenews.api

import com.example.cheesecakenews.model.Game
import com.example.cheesecakenews.model.GameItem
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(private val newsApi: NewsApi) {
    fun games(apiKey: String): Observable<List<Game>> {
        return newsApi.games(apiKey)
    }
    fun gameItem(id: Long, apiKey: String): Observable<GameItem> {
        return newsApi.gameItem(apiKey, id)
    }
    fun gameCheckout(apiKey: String): Observable<String> {
        return newsApi.gameCheckout(apiKey)
    }

}