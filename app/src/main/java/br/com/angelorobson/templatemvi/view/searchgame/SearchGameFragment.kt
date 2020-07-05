package br.com.angelorobson.templatemvi.view.searchgame

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.searchgame.widgets.GameFoundAdapter
import br.com.angelorobson.templatemvi.view.utils.setVisibleOrGone
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
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

        game_search_view.requestFocus()
        showKeyboard()

        val disposable = Observable.mergeArray(
                adapter.gameClicks.map { GameFoundClickedEvent(it) },
                game_search_view.textChanges()
                        .skip(1)
                        .debounce(100, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map {
                            game_search_progress_horizontal.setVisibleOrGone(true)
                            SearchGameByTermEvent(it.toString())
                        }
        )
                .compose(getViewModel(SearchGameViewModel::class).init(InitialEvent()))
                .subscribe(
                        { model ->
                            when (model.searchGameResult) {
                                is SearchGameResult.Loading -> {
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)
                                }
                                is SearchGameResult.GamesFoundByTerm -> {
                                    val spotlights = model.searchGameResult.spotlights
                                    game_search_not_found_text_view.setVisibleOrGone(spotlights.isEmpty())
                                    adapter.submitList(spotlights)
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)
                                }
                                is SearchGameResult.Error -> {
                                    game_search_progress_horizontal.setVisibleOrGone(model.searchGameResult.isLoading)
                                }
                            }
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)
    }

    private fun showKeyboard() {
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(game_search_view, 0)
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