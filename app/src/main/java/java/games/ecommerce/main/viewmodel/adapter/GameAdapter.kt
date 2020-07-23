package java.games.ecommerce.main.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.game_card.view.*
//import kotlinx.android.synthetic.main.game_card.view.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.utils.loadImgCroped


class GameAdapter(
    private val gameList: List<Game>,
    private val onClick: (Game) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.game_card, parent, false)
        return GameViewHolder(cellsForRow, onClick)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GameViewHolder).bind(gameList[position])
    }

}

class GameViewHolder(private val view: View, private val onClick: (Game) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bind(game: Game) {
        itemView.apply {
            gameTitle.text = game.title
            gameNewPrice.text = game.price.subtract(game.discount).toString()
            gameOldPrice.text = game.price.toString()
            gamePublisher.text = game.publisher
            gameImage.loadImgCroped(game.image)
            gameCard.setOnClickListener {
                onClick(game)
            }
        }
    }
}