package br.com.angelorobson.templatemvi.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposable = Observable.empty<HomeEvent>()
                .compose(getViewModel(HomeViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.homeResult) {
                                is HomeResult.SpotlightsLoaded -> {
                                    print(model.homeResult.spotlights)
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
