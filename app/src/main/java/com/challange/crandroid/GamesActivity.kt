package com.challange.crandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.challange.crandroid.adapter.GamesAdapter
import com.challange.crandroid.api.GameCheckoutServiceInitializer
import com.challange.crandroid.data.response.Game
import kotlinx.android.synthetic.main.activity_games.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val NUM_COLUMNS = 2

class GamesActivity : AppCompatActivity(), GamesAdapter.OnGameTapListener {

    private var mGames: ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        loadGames()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_games_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onGameTap(position: Int) {
        val game = mGames[position]
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra("gameId", game.id)
        startActivity(intent)
    }

    private fun loadGames() {
        GameCheckoutServiceInitializer().gameCheckoutService().getGames()
            .enqueue(object: Callback<List<Game>> {
                override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                    if (response.isSuccessful) {
                        mGames = response.body() as ArrayList<Game>
                        initRecyclerView()
                    } else {
                        TODO("Notify can't load games")
                    }
                }

                override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                    TODO("Notify can't load games")
                }
            })
    }

    private fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayout.VERTICAL)
        val adapter = GamesAdapter(this, mGames, this)

        val recyclerView: RecyclerView = findViewById(R.id.gamesRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        progressIndicator.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
