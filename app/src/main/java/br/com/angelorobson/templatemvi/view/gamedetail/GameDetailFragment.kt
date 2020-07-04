package br.com.angelorobson.templatemvi.view.gamedetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.FragmentGameDetailBinding
import br.com.angelorobson.templatemvi.view.utils.BindingFragment


class GameDetailFragment : BindingFragment<FragmentGameDetailBinding>() {

    private val args: GameDetailFragmentArgs by navArgs()

    override fun getLayoutResId(): Int = R.layout.fragment_game_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.item = args.game
    }

}