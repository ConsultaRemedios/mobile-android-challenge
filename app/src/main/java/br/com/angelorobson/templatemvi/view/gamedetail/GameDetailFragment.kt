package br.com.angelorobson.templatemvi.view.gamedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.FragmentGameDetailBinding
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.utils.BindingFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


class GameDetailFragment : BindingFragment<FragmentGameDetailBinding>() {

    private val args: GameDetailFragmentArgs by navArgs()
    private val mCompositeDisposable = CompositeDisposable()


    override fun getLayoutResId(): Int = R.layout.fragment_game_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val disposable = Observable.empty<GameDetailEvent>()
                .compose(getViewModel(GameDetailViewModel::class).init(InitialEvent(args.id)))
                .subscribe(
                        { model ->
                            when (model.gameDetailResult) {
                                is GameDetailResult.Loading -> {
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                }
                                is GameDetailResult.GameLoaded -> {
                                    val spotlight = model.gameDetailResult.spotlight
                                    binding.item = spotlight
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                }
                                is GameDetailResult.Error -> {
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                }
                            }
                        },
                        {

                        }
                )

        mCompositeDisposable.add(disposable)
    }

}