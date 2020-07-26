package java.games.ecommerce.main.ui.activity.gamelist

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_game.view.*
//import kotlinx.android.synthetic.main.card_game.view.*
import java.games.ecommerce.R
import java.games.ecommerce.main.data.model.Game
import java.games.ecommerce.utils.asCurrency
import java.games.ecommerce.utils.asStrokeText
import java.games.ecommerce.utils.loadImgCroped


class GameAdapter(
    private val gameList: List<Game>,
    private val onClick: (Game) -> Unit
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
            gameNewPrice.text = (game.price - game.discount).asCurrency()
            gameOldPrice.text = ("de " + game.price.asCurrency()).asStrokeText()
            gamePublisher.text = game.publisher
            gameImage.loadImgCroped(game.image)
            gameCard.setOnClickListener {
                onClick(game)
            }
        }
    }
}