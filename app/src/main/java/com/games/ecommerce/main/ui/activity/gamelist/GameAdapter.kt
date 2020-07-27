package com.games.ecommerce.main.ui.activity.gamelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.GameRepositoryResponse
import com.games.ecommerce.utils.asCurrency
import com.games.ecommerce.utils.asStrokeText
import com.games.ecommerce.utils.loadImgCroped
import kotlinx.android.synthetic.main.card_game.view.*


class GameAdapter(
    private val gameRepositoryResponseList: List<GameRepositoryResponse>,
    private val onClick: (GameRepositoryResponse) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.card_game, parent, false)
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
    view: View,
    private val onClick: (GameRepositoryResponse) -> Unit
) :
    RecyclerView.ViewHolder(view) {

    fun bind(gameRepositoryResponse: GameRepositoryResponse) {
        itemView.apply {
            gameTitle.text = gameRepositoryResponse.title
            gameNewPrice.text = gameRepositoryResponse.price.asCurrency()
            gameOldPrice.text =
                String.format("de %s", gameRepositoryResponse.originalPrice.asCurrency())
                    .asStrokeText()
            gamePublisher.text = gameRepositoryResponse.publisher
            gameImage.loadImgCroped(gameRepositoryResponse.image)
            gameCard.setOnClickListener {
                onClick(gameRepositoryResponse)
            }
        }
    }
}