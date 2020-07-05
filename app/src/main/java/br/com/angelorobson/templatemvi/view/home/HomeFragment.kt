package br.com.angelorobson.templatemvi.view.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.home.widgets.GameAdapter
import br.com.angelorobson.templatemvi.view.utils.GridSpacingItemDecoration
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mCompositeDisposable = CompositeDisposable()
    private val itemsCarousel = arrayListOf<CarouselItem>()

    override fun onStart() {
        super.onStart()

        val gameAdapter = GameAdapter()
        setupRecyclerView(gameAdapter)

        val disposable = Observable.mergeArray(
                gameAdapter.gameClicks.map { GameClickedEvent(it) }
        )
                .compose(getViewModel(HomeViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.homeResult) {
                                is HomeResult.Loading -> {
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                }
                                is HomeResult.SpotlightsLoaded -> {
                                    val spotlights = model.homeResult.spotlights
                                    gameAdapter.submitList(spotlights)
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                }
                                is HomeResult.BannerLoaded -> {
                                    val banners = model.homeResult.banners
                                    itemsCarousel.addAll(banners.map {
                                        CarouselItem(imageUrl = it.image)
                                    })
                                }
                                is HomeResult.Error -> {
                                    print(model.homeResult.errorMessage)
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                }
                            }

                            home_carousel.addData(itemsCarousel)
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)

    }

    private fun hideOrVisibleProgressBar(isVisible: Boolean) {
        home_progress_bar?.apply {
            visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView(gameAdapter: GameAdapter) {
        home_spotlights_recycler_view.apply {
            val spanCount = 2
            val spacing = 40
            val includeEdge = true
            val itemDecoration = GridSpacingItemDecoration(spanCount, spacing, includeEdge)

            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
            addItemDecoration(itemDecoration)
        }
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }

}
