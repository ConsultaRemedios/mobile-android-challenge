package com.example.mobile_android_challenge.view_model

import android.content.Context

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cheesecakenews.api.ApiClient
import com.example.cheesecakenews.api.NewsApi
import com.example.cheesecakenews.model.Game
import com.example.mobile_android_challenge.SchedulerProvider
import com.example.mobile_android_challenge.repository.GamesRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GamesViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    private val _data = MutableLiveData<List<Game>>()
    val data: LiveData<List<Game>> = _data
    private val disposable = CompositeDisposable()

    fun fetchGames(context: Context) {
        var repository = GamesRepository(context)
        _data.value = repository.getGames()
        if(_data.value.isNullOrEmpty()) {
            disposable.add(
                api.games(NewsApi.API_KEY).subscribeOn(schedulers.io())
                    .observeOn(schedulers.mainThread())
                    .subscribe({
                        _data.value = it
                    }, {
                        _data.value = emptyList()
                        Log.e("ERROR", it.message)
                    }))
        }
    }
}