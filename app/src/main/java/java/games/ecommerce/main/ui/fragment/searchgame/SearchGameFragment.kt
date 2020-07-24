package java.games.ecommerce.main.ui.fragment.searchgame

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_searchgame.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import java.games.ecommerce.main.ui.activity.gamelist.GameListViewModel
import java.games.ecommerce.utils.ViewModelFactory
import java.games.ecommerce.utils.observe
import javax.inject.Inject

class SearchGameFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: GameListViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(GameListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_searchgame, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        observe(viewModel.gamesFound) {
            addGames(it)
        }
        observe(viewModel.game) {
            startDetail(it)
        }
    }
    private fun addGames(games: List<Game>) {
        recyclerview_searchgame.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter =
                SearchGameAdapter(games) {
                    viewModel.gameById(it.id)
                }
        }
    }
    private fun startDetail(game: Game) {
        val intent = Intent(activity, GameDetailActivity::class.java)
        intent.putExtra("game", game)
        startActivity(intent)
    }

}