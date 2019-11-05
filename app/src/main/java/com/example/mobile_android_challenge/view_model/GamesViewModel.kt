package com.example.mobile_android_challenge.view_model

import android.content.Context

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_android_challenge.SchedulerProvider
import com.example.mobile_android_challenge.api.ApiClient
import com.example.mobile_android_challenge.api.GamesApi
import com.example.mobile_android_challenge.model.Game
import com.example.mobile_android_challenge.repository.CartRepository
import com.example.mobile_android_challenge.repository.GamesRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GamesViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    private val _data = MutableLiveData<List<Game>>()
    val data: LiveData<List<Game>> = _data
    private val disposable = CompositeDisposable()

    private val _sizeCart = MutableLiveData<Int>()
    val sizeCart: LiveData<Int> = _sizeCart

    fun loadGames(context: Context) {
        var repository = GamesRepository(context)
        _data.value = repository.getGames()
        if(_data.value.isNullOrEmpty()) {
            fethGames()
        }
    }

    fun fethGames() {
        disposable.add(
            api.games(GamesApi.API_KEY).subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe({
                    _data.value = it
                }, {
                    _data.value = emptyList()
                    Log.e("ERROR", it.message)
                }))
    }

    fun loadCountCart(context: Context) {
        var repository = CartRepository(context)
        _sizeCart.value = repository.countCart()
    }
}