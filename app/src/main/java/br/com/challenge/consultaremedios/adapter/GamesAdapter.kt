package br.com.challenge.consultaremedios.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.consultaremedios.R
import br.com.challenge.consultaremedios.model.Game
import br.com.challenge.consultaremedios.utils.GenericUtils.Companion.brazilianNumberFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GamesAdapter(private val context: Context,
                   private var games: List<Game>,
                   private val onGameTapListener: OnGameTapListener):
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_game, parent, false)
        return ViewHolder(view, onGameTapListener)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
        Glide.with(context)
            .load(game.image)
            .apply(requestOptions)
            .into(holder.boxArt)

        holder.publisher.apply { text = game.publisher }
        holder.title.apply { text = game.title }
        holder.price.apply {
            text = brazilianNumberFormat().format(game.price)
            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.priceWithDiscount.apply { text = brazilianNumberFormat().format(game.price.minus(game.discount)) }
    }

    class ViewHolder(itemView: View, onGameTapListener: OnGameTapListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val onGameTapListener: OnGameTapListener

        init {
            itemView.setOnClickListener(this)
            this.onGameTapListener = onGameTapListener
        }

        var boxArt: ImageView = itemView.findViewById(R.id.boxart)
        var publisher: TextView = itemView.findViewById(R.id.publisher)
        var title: TextView = itemView.findViewById(R.id.title)
        var price: TextView = itemView.findViewById(R.id.price)
        var priceWithDiscount: TextView = itemView.findViewById(R.id.price_with_discount)

        override fun onClick(v: View?) {
            onGameTapListener.onGameTap(adapterPosition)
        }
    }

    interface OnGameTapListener { fun onGameTap(position: Int) }
}