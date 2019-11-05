package com.example.mobile_android_challenge.repository

import javax.inject.Inject
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mobile_android_challenge.data.DBHelper
import com.example.mobile_android_challenge.model.Game

import com.example.mobile_android_challenge.view_model.GamesViewModel
import com.example.mobile_android_challenge.view_model.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable

class GameRepository(context: Context) {

    @Inject
    lateinit var newsVMFactory: ViewModelFactory<GamesViewModel>

    protected val ItemsObserver = Observer<List<Game>>(::onItemsFetched)

    lateinit var dbHelper : DBHelper
    var listGames: MutableList<Game> = mutableListOf()


    private val _data = MutableLiveData<List<Game>>()
    val data: LiveData<List<Game>> = _data
    private val disposable = CompositeDisposable()

    init {
        dbHelper = DBHelper(context.applicationContext)
        listGames = dbHelper.readAllGames();
    }

    private fun onItemsFetched(list: List<Game>?) {
        if (list != null) {
            list.forEach {
                addGame(it)
            }
            listGames = dbHelper.readAllGames()
        }
    }

    fun addGame(game: Game){
        dbHelper.insertGames(game)
    }

    fun getGames(): ArrayList<Game> {
        return dbHelper.readAllGames()
    }
}