package com.benhurqs.cards.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.base.actions.Actions
import com.benhurqs.base.adapter.DefaultViewHolder
import com.benhurqs.base.utils.ImageUtils
import com.benhurqs.base.utils.Utils
import com.benhurqs.cards.R
import com.benhurqs.network.entities.Spotlight
import kotlinx.android.synthetic.main.card_item.view.*

class CardsAdapter (val list: List<Spotlight>?, val onClickItem: (spotlight: Spotlight) -> Unit) : RecyclerView.Adapter<DefaultViewHolder>(){

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val spotlight = list?.get(position) ?: return

        ImageUtils.loadImage(holder.itemView.card_img, spotlight.image)
        holder.itemView.card_publisher.text = spotlight.publisher
        holder.itemView.card_title.text = spotlight.title
        holder.itemView.card_price.text = Utils.formatPrice(spotlight.price - spotlight.discount)
        holder.itemView.card_last_price.text = Utils.formatPrice(spotlight.price)
        holder.itemView.card_last_price.paintFlags = holder.itemView.card_last_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener {
            onClickItem(spotlight)
        }
    }
}