package com.games.ecommerce.main.ui.fragment.searchgame

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.GameServiceResponse
import com.games.ecommerce.main.ui.activity.gamedetails.GameDetailActivity
import com.games.ecommerce.main.ui.activity.gamelist.GameListViewModel
import com.games.ecommerce.utils.ViewModelFactory
import com.games.ecommerce.utils.observe
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_searchgame.*
import javax.inject.Inject

class SearchGameFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GameListViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(GameListViewModel::class.java)
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
        observe(viewModel.gameServiceResponse) {
            startDetail(it)
        }
    }

    private fun addGames(gameRepositoryResponse: List<GameRepositoryResponse>) {
        recyclerview_searchgame.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter =
                SearchGameAdapter(gameRepositoryResponse) {
                    viewModel.gameById(it.id)
                }
        }
    }

    private fun startDetail(gameRepositoryResponse: GameRepositoryResponse) {
        val intent = Intent(activity, GameDetailActivity::class.java)
        intent.putExtra("game", gameRepositoryResponse)
        startActivity(intent)
        destroyFragment()
    }

    private fun destroyFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

}