package com.example.mobile_android_challenge.view.games_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cheesecakenews.model.Game
import com.example.mobile_android_challenge.R
import kotlinx.android.synthetic.main.item_game_adapter.view.*

class GamesAdapter(val clickListener: ((Game) -> Unit)?) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    var gamesItems: List<Game>
    init {
        gamesItems = listOf()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(gamesItem: Game, clickListener: ((Game) -> Unit)?) {
            itemView.tv_console_name.text = gamesItem.platform
            itemView.tv_price.text = itemView.context.getString(R.string.item_price, gamesItem.price.toString())
            itemView.tv_title_game.text = gamesItem.name
            itemView.setOnClickListener { clickListener?.let { it1 -> it1(gamesItem) } }
            Glide.with(itemView)
                .load("${gamesItem?.image}")
                .into(itemView.img_cover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = gamesItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        (holder as ViewHolder).bind(gamesItems[position], clickListener)
    }

    fun updateList() {
        notifyDataSetChanged()
    }

    fun update(newsItems: List<Game>) {
        this.gamesItems = emptyList()
        this.gamesItems = newsItems
        updateList()
    }
}
