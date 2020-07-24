package java.games.ecommerce.main.ui.activity.gamelist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Banner
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import java.games.ecommerce.main.ui.fragment.searchgame.SearchGameFragment
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.observe
import javax.inject.Inject

class ListActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var isFragmentVisible = false
    private val viewModel: GameListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GameListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupView()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {

        searchTextGame.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
//                checkFragmentVisibility(s.toString().length)
                checkFragmentVisibility(s.toString().length)
                viewModel.searchGame(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }
        })
    }

    fun checkFragmentVisibility(length: Int){
        if(length < 1 && isFragmentVisible) {
            isFragmentVisible = false
            switchFragment(false)
            return
        }
        if (length >= 1 && !isFragmentVisible) {
            isFragmentVisible = true
            switchFragment(true)
        }

    }

    private fun switchFragment(visible: Boolean) {
        val fragment = SearchGameFragment()
        val fragmentManager = supportFragmentManager
        if (visible) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.listActivity, fragment)
            fragmentTransaction.commit()
            return
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.listActivity, Fragment())
        fragmentTransaction.commit()
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
        observe(viewModel.isSearchTextVisible) {
            switchFragment(it)
        }
    }

    private fun addGames(games: List<Game>) {
        games_recyclerView.apply {
            layoutManager = GridLayoutManager(this@ListActivity, 2)
            adapter =
                GameAdapter(games) {
                    startDetail(it)
                }
        }
    }

    private fun addBanners(banners: List<Banner>) {
        banners_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                BannerAdapter(
                    banners
                ) {
                    println("clicked banner")
                }
        }
    }

    private fun startDetail(game: Game) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra("game", game)
        startActivity(intent)
    }

}
