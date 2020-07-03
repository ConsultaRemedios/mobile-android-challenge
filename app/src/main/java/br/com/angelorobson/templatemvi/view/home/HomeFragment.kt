package br.com.angelorobson.templatemvi.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.home.widgets.GameAdapter
import br.com.angelorobson.templatemvi.view.pullrequest.widgets.PullRequestAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_pull_request.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameAdapter = GameAdapter()

        home_spotlights_recycler_view.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
        }

        val disposable = Observable.empty<HomeEvent>()
                .compose(getViewModel(HomeViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.homeResult) {
                                is HomeResult.SpotlightsLoaded -> {
                                    val spotlights = model.homeResult.spotlights
                                    gameAdapter.submitList(spotlights)
                                }
                                is HomeResult.BannerLoaded -> {
                                    print(model.homeResult.banners)
                                }
                                is HomeResult.Error -> {
                                    print(model.homeResult.errorMessage)
                                }
                            }
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)

    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }

}
