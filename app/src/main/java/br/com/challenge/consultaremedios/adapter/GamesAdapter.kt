package br.com.challenge.consultaremedios.adapter

import android.content.Context
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

class GamesAdapter(context: Context, games: List<Game>, onGameTapListener: OnGameTapListener):
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    private val mContext = context
    private var mGames = games
    private val mOnGameTapListener = onGameTapListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_game, parent, false)
        return ViewHolder(view, mOnGameTapListener)
    }

    override fun getItemCount(): Int {
        return mGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = mGames[position]

        val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
        Glide.with(mContext)
            .load(game.image)
            .apply(requestOptions)
            .into(holder.boxArt)

        holder.publisher.text = game.publisher
        holder.title.text = game.title
        holder.price.text = brazilianNumberFormat().format(game.price)
        holder.priceWithDiscount.text = brazilianNumberFormat().format(game.price - game.discount)
    }

    class ViewHolder(itemView: View, onGameTapListener: OnGameTapListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onGameTapListener: OnGameTapListener

        init {
            itemView.setOnClickListener(this)
            this.onGameTapListener = onGameTapListener
        }

        var boxArt: ImageView = itemView.findViewById(R.id.item_game_boxart)
        var publisher: TextView = itemView.findViewById(R.id.item_game_publisher)
        var title: TextView = itemView.findViewById(R.id.item_game_title)
        var price: TextView = itemView.findViewById(R.id.item_game_price)
        var priceWithDiscount: TextView = itemView.findViewById(R.id.item_game_price_with_discount)

        override fun onClick(v: View?) {
            onGameTapListener.onGameTap(adapterPosition)
        }
    }

    interface OnGameTapListener {
        fun onGameTap(position: Int)
    }
}