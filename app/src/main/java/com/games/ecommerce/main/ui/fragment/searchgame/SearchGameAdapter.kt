package com.games.ecommerce.main.ui.fragment.searchgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.main.data.model.GameServiceResponse
import com.games.ecommerce.utils.asCurrency
import kotlinx.android.synthetic.main.card_searchgame.view.*


class SearchGameAdapter(
    private val gameRepositoryResponseList: List<GameRepositoryResponse>,
    private val onClick: (GameRepositoryResponse) -> Unit
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
        return gameRepositoryResponseList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GameViewHolder).bind(gameRepositoryResponseList[position])
    }

}

class GameViewHolder(
    private val view: View,
    private val onClick: (GameRepositoryResponse) -> Unit
) :
    RecyclerView.ViewHolder(view) {
    fun bind(gameRepositoryResponse: GameRepositoryResponse) {
        itemView.apply {
            searchGameTitle.text = gameRepositoryResponse.title
            searchGamePrice.text = gameRepositoryResponse.price.asCurrency()
            card_searchgame.setOnClickListener {
                onClick(gameRepositoryResponse)
            }
        }
    }
}