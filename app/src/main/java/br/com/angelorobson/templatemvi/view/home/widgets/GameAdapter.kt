package br.com.angelorobson.templatemvi.view.home.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.GameItemBinding
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import kotlinx.android.extensions.LayoutContainer

class GameAdapter : ListAdapter<Spotlight, SpotlightViewHolder>(DiffUtilCallback<Spotlight>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder {
        val binding = DataBindingUtil.bind<GameItemBinding>(
                LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                )
        )

        return SpotlightViewHolder(binding?.root!!, binding)
    }

    override fun onBindViewHolder(holder: SpotlightViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.game_item
    }

}

class SpotlightViewHolder(
        override val containerView: View,
        private val binding: GameItemBinding?
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(spotlight: Spotlight) {
        binding?.apply {
            item = spotlight
            executePendingBindings()
        }
    }

}