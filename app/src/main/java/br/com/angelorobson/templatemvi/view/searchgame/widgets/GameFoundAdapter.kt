package br.com.angelorobson.templatemvi.view.searchgame.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.GameFoundItemBinding
import br.com.angelorobson.templatemvi.databinding.GameItemBinding
import br.com.angelorobson.templatemvi.model.domains.Spotlight
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

class GameFoundAdapter : ListAdapter<Spotlight, SpotlightViewHolder>(DiffUtilCallback<Spotlight>()) {

    private val gameClicksSubject = PublishSubject.create<Int>()
    val gameClicks: Observable<Spotlight> = gameClicksSubject.map { position -> getItem(position) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder {
        val binding = DataBindingUtil.bind<GameFoundItemBinding>(
                LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                )
        )

        return SpotlightViewHolder(binding?.root!!, binding, gameClicksSubject)
    }

    override fun onBindViewHolder(holder: SpotlightViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.game_found_item
    }

}

class SpotlightViewHolder(
        override val containerView: View,
        private val binding: GameFoundItemBinding?,
        private val gameClicksSubject: PublishSubject<Int>
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(spotlight: Spotlight) {
        binding?.apply {
            item = spotlight
            gameItemLinearLayout.clicks().map { adapterPosition }.subscribe(gameClicksSubject)
            executePendingBindings()
        }
    }

}