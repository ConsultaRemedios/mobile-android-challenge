package br.com.angelorobson.templatemvi.view.searchgame

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.searchgame.widgets.GameFoundAdapter
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_game.*
import java.util.concurrent.TimeUnit

class SearchGameFragment : Fragment(R.layout.fragment_search_game) {

    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mLayoutManager: LinearLayoutManager


    override fun onStart() {
        super.onStart()

        val adapter = GameFoundAdapter()

        setupRecyclerView(adapter)

        val disposable = Observable.mergeArray(
                adapter.gameClicks.map { GameFoundClickedEvent(it) },
                game_search_view.textChanges()
                        .debounce(100, TimeUnit.MILLISECONDS)
                        .map { SearchGameByTermEvent(it.toString()) }
        )
                .compose(getViewModel(SearchGameViewModel::class).init(InitialEvent()))
                .subscribe(
                        { model ->
                            when (model.searchGameResult) {
                                is SearchGameResult.Loading -> {
//                                    binding.isProgressBarVisible = model.searchGameResult.isLoading
                                }
                                is SearchGameResult.GamesFoundByTerm -> {
                                    val spotlights = model.searchGameResult.spotlights
                                    adapter.submitList(spotlights)
                                }
                                is SearchGameResult.Error -> {

                                }
                            }
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)
    }

    private fun setupRecyclerView(repositoryAdapter: GameFoundAdapter) {
        mLayoutManager = LinearLayoutManager(context)

        search_game_recycler_view.apply {
            layoutManager = mLayoutManager
            adapter = repositoryAdapter
            addItemDecoration(
                    DividerItemDecoration(
                            context,
                            mLayoutManager.orientation
                    )
            )
        }
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }

}