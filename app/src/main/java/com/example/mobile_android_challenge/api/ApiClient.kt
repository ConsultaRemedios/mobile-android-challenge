package com.example.mobile_android_challenge.api

import com.example.mobile_android_challenge.model.Game
import com.example.mobile_android_challenge.model.GameItem
import io.reactivex.Observable
import javax.inject.Inject

class ApiClient @Inject constructor(private val gamesApi: GamesApi) {
    fun games(apiKey: String): Observable<List<Game>> {
        return gamesApi.games(apiKey)
    }
    fun gameItem(id: Long, apiKey: String): Observable<GameItem> {
        return gamesApi.gameItem(apiKey, id)
    }
    fun gameCheckout(apiKey: String): Observable<String> {
        return gamesApi.gameCheckout(apiKey)
    }

}