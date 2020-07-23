package java.games.ecommerce.main.view

import android.os.Bundle
import android.view.Window
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.list_activity.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.viewmodel.GameListViewModel
import java.games.ecommerce.main.viewmodel.adapter.BannerAdapter
import java.games.ecommerce.main.viewmodel.adapter.GameAdapter
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.observe
import javax.inject.Inject

class ListActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: GameListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        viewModel.fetchData()
    }

    private fun setupObservers() {
        observe(viewModel.games) {
            addGames(it)
        }
        observe(viewModel.banners) {
            addBanners(it)
        }
    }

    private fun addGames(games: List<Game>) {
        games_recyclerView.apply {
            layoutManager = GridLayoutManager(this@ListActivity, 2)
            adapter = GameAdapter(games) {
                println("teste")
            }
        }
    }

    private fun addBanners(banners: List<Banner>) {
        banners_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = BannerAdapter(banners) {
                println("clicked banner")
            }
        }
    }

}
