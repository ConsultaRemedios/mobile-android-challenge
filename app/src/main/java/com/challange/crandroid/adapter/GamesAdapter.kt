package com.challange.crandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.challange.crandroid.R
import com.challange.crandroid.data.response.Game
import java.text.NumberFormat
import java.util.*

class GamesAdapter(context: Context, games: ArrayList<Game>) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    private var mGames = games
    private val mNumberFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"))
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_game_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = mGames[position]

        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
        Glide.with(mContext)
            .load(game.image)
            .apply(requestOptions)
            .into(holder.image)

        holder.platform.text = game.platform
        holder.title.text = game.title
        holder.price.text = mNumberFormat.format(game.price)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.game_image)
        var platform: TextView = itemView.findViewById(R.id.platform)
        var title: TextView = itemView.findViewById(R.id.game_title)
        var price: TextView = itemView.findViewById(R.id.game_price)

    }
}