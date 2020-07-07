package br.com.angelorobson.templatemvi.view.home

import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.home.widgets.GameAdapter
import br.com.angelorobson.templatemvi.view.utils.GridSpacingItemDecoration
import br.com.angelorobson.templatemvi.view.utils.setVisibleOrGone
import br.com.angelorobson.templatemvi.view.utils.toastWithResourceString
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_home.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener


class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        var myScrollViewerInstanceState: Parcelable? = null
    }

    private val mCompositeDisposable = CompositeDisposable()
    private val itemsCarousel = arrayListOf<CarouselItem>()
    private var spotlights = listOf<Spotlight>()


    override fun onResume() {
        super.onResume()

        if (myScrollViewerInstanceState != null) {
            my_scroll_viewer.onRestoreInstanceState(myScrollViewerInstanceState)
        }

        val gameAdapter = GameAdapter()
        setupRecyclerView(gameAdapter)
        val bannerClickSubject = PublishSubject.create<CarouselItem>()
        val initObservable = Observable.just(1)
        var count = 0

        val disposable = Observable.mergeArray(
                gameAdapter.gameClicks.map { GameClickedEvent(it) },
                home_search_view.clicks().map { SearchViewClickedEvent },
                home_cart_floating_action_button.clicks()
                        .filter {
                            if (count > 0) {
                                true
                            } else {
                                context?.toastWithResourceString(R.string.add_item_cart)
                                false
                            }
                        }
                        .map { CartActionButtonClickedEvent },
                bannerClickSubject.map { BannerClickedEvent(it.caption ?: "") },
                home_try_again_button.clicks().map { InitialEvent },
                initObservable.map { InitialEvent }
        )
                .compose(getViewModel(HomeViewModel::class))
                .subscribe(
                        { model ->
                            when (model.homeResult) {
                                is HomeResult.Loading -> {
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                    home_try_again_button.setVisibleOrGone(false)
                                }
                                is HomeResult.SpotlightsLoaded -> {
                                    val list = model.homeResult.spotlights
                                    spotlights = list
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                    home_try_again_button.setVisibleOrGone(false)
                                }
                                is HomeResult.BannerLoaded -> {
                                    val banners = model.homeResult.banners
                                    itemsCarousel.addAll(banners.map {
                                        CarouselItem(imageUrl = it.image, caption = it.url)
                                    })
                                    home_try_again_button.setVisibleOrGone(false)
                                }
                                is HomeResult.ShoppingCartItemCount -> {
                                    count = model.homeResult.count
                                    home_cart_floating_action_button.count = count
                                    home_try_again_button.setVisibleOrGone(false)
                                }
                                is HomeResult.Error -> {
                                    hideOrVisibleProgressBar(model.homeResult.isLoading)
                                    home_try_again_button.setVisibleOrGone(true)
                                }
                            }

                            gameAdapter.submitList(spotlights)
                            home_carousel.addData(itemsCarousel)
                            home_carousel.onItemClickListener = object : OnItemClickListener {
                                override fun onClick(position: Int, carouselItem: CarouselItem) {
                                    bannerClickSubject.onNext(carouselItem)
                                }

                                override fun onLongClick(position: Int, dataObject: CarouselItem) {
                                    bannerClickSubject.onNext(dataObject)
                                }

                            }
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

    override fun onPause() {
        super.onPause()
        myScrollViewerInstanceState = my_scroll_viewer.onSaveInstanceState()
    }

}
