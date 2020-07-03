package br.com.angelorobson.templatemvi.view.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.home.widgets.GameAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        val gameAdapter = GameAdapter()

        home_spotlights_recycler_view.apply {
            val spanCount = 2
            val spacing = 40
            val includeEdge = true

            val itemDecoration = GridSpacingItemDecoration(spanCount, spacing, includeEdge)

            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
            addItemDecoration(itemDecoration)
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
                                    val itemsCarrousel = model.homeResult.banners.map {
                                        CarouselItem(
                                                imageUrl = it.image
                                        )
                                    }

                                    carousel.addData(itemsCarrousel)

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


class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }

}
