package br.com.angelorobson.templatemvi.view.gamedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.FragmentGameDetailBinding
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.utils.BindingFragment
import br.com.angelorobson.templatemvi.view.utils.setVisibleOrGone
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_game_detail.*


class GameDetailFragment : BindingFragment<FragmentGameDetailBinding>() {

    private val args: GameDetailFragmentArgs by navArgs()
    private val mCompositeDisposable = CompositeDisposable()

    override fun getLayoutResId(): Int = R.layout.fragment_game_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val disposable = Observable.mergeArray(
                game_Detail_add_item_card_floating_action_button.clicks().map {
                    AddOrRemoveItemCardEvent(binding.item)
                },
                game_detail_try_again_button.clicks().map { InitialEvent(args.id) }
        )
                .compose(getViewModel(GameDetailViewModel::class).init(InitialEvent(args.id)))
                .subscribe(
                        { model ->
                            when (model.gameDetailResult) {
                                is GameDetailResult.Loading -> {
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                    game_detail_try_again_button.setVisibleOrGone(false)
                                    game_Detail_add_item_card_floating_action_button.isEnabled = true
                                }
                                is GameDetailResult.GameLoaded -> {
                                    val spotlight = model.gameDetailResult.spotlight
                                    binding.item = spotlight
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                    game_detail_try_again_button.setVisibleOrGone(false)
                                    game_Detail_add_item_card_floating_action_button.isEnabled = true
                                }
                                is GameDetailResult.ItemCartStatusResult -> {
                                    binding.isItemAdded = model.gameDetailResult.isCartItemAdded
                                    game_detail_try_again_button.setVisibleOrGone(false)
                                    game_Detail_add_item_card_floating_action_button.isEnabled = true

                                }
                                is GameDetailResult.Error -> {
                                    binding.isProgressBarVisible = model.gameDetailResult.isLoading
                                    game_detail_try_again_button.setVisibleOrGone(true)
                                    game_Detail_add_item_card_floating_action_button.isEnabled = false
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