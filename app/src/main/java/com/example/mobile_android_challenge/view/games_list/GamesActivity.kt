package com.example.mobile_android_challenge.view.games_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.model.Game
import com.example.mobile_android_challenge.view.cart.CartActivity
import com.example.mobile_android_challenge.view.game.GameActivity
import com.example.mobile_android_challenge.view.games_list.adapter.GamesAdapter
import com.example.mobile_android_challenge.view_model.GamesViewModel
import com.example.mobile_android_challenge.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toobar.*
import javax.inject.Inject

class GamesActivity : AppCompatActivity() {
    @Inject
    lateinit var newsVMFactory: ViewModelFactory<GamesViewModel>

    private val newsViewModel by lazy {
        ViewModelProviders.of(this, newsVMFactory)[GamesViewModel::class.java]
    }

    protected val ItemsObserver = Observer<List<Game>>(::onItemsFetched)

    private lateinit var adapter: GamesAdapter
    var layoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel.data.observe(this, ItemsObserver)
        newsViewModel.fetchGames(this.baseContext)
        img_cart.setOnClickListener {
            val intent = Intent(this.baseContext, CartActivity::class.java)
            this.baseContext.startActivity(intent)
        }
    }


    private fun onItemsFetched(list: List<Game>?) {
        if (list != null) {
            updateAdapter(list)
        }
    }

    private fun updateAdapter(list: List<Game>) {
        rc_games.layoutManager = layoutManager
        rc_games.setHasFixedSize(true)
        adapter = GamesAdapter({ game: Game -> partItemClicked(game) } )
        adapter.update(list)
        rc_games.adapter = adapter
        progress_bar.visibility = View.GONE
    }

    private fun partItemClicked(game: Game) {
        val intent = Intent(this.baseContext, GameActivity::class.java)
        intent.putExtra("game_id", game.id)
        startActivity(intent)
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
    }

}
