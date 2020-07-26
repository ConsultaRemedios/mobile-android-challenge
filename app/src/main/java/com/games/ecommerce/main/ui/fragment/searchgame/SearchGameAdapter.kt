package com.games.ecommerce.main.ui.fragment.searchgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.Game
import com.games.ecommerce.utils.asCurrency
import kotlinx.android.synthetic.main.card_searchgame.view.*


class SearchGameAdapter(
    private val gameList: List<Game>,
    private val onClick: (Game) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.card_searchgame, parent, false)
        return GameViewHolder(
            cellsForRow,
            onClick
        )
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GameViewHolder).bind(gameList[position])
    }

}

class GameViewHolder(private val view: View, private val onClick: (Game) -> Unit) :
    RecyclerView.ViewHolder(view) {
    fun bind(game: Game) {
        itemView.apply {
            searchGameTitle.text = game.title
            searchGamePrice.text = (game.price - game.discount).asCurrency()
            card_searchgame.setOnClickListener {
                onClick(game)
            }
        }
    }
}