package br.com.angelorobson.templatemvi.view.searchgame

import androidx.fragment.app.Fragment
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


class SearchGameFragment : Fragment(R.layout.fragment_search_game) {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        val disposable = Observable.empty<SearchGameEvent>()
                .compose(getViewModel(SearchGameViewModel::class).init(InitialEvent()))
                .subscribe(
                        { model ->
                            when (model.searchGameResult) {
                                is SearchGameResult.Loading -> {
//                                    binding.isProgressBarVisible = model.searchGameResult.isLoading
                                }
                                is SearchGameResult.GamesFoundByTerm -> {
                                    model.searchGameResult.spotlights
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

}