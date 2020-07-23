package com.benhurqs.checkout.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.base.adapter.DefaultViewHolder
import com.benhurqs.base.utils.ImageUtils
import com.benhurqs.base.utils.Utils
import com.benhurqs.checkout.R
import com.benhurqs.network.entities.Cart
import kotlinx.android.synthetic.main.cart_item.view.*

class CheckoutAdapter (private val list: ArrayList<Cart>?) : RecyclerView.Adapter<DefaultViewHolder>(){

    private var qtd = 1

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DefaultViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.cart_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        var item = list?.get(position) ?: return
        ImageUtils.loadImage(holder.itemView.cart_item_img, item.image)
        holder.itemView.cart_item_title.text = item.title
        holder.itemView.cart_item_qtd.text = item.qtd.toString()

        holder.itemView.card_item_price.text = Utils.formatPrice(item.price - item.discount)
        holder.itemView.card_item_last_price.text = Utils.formatPrice(item.price)
        holder.itemView.card_item_last_price.paintFlags = holder.itemView.card_item_last_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.card_item_add.setOnClickListener {
            qtd++
            holder.itemView.cart_item_qtd.text = qtd.toString()
        }

        holder.itemView.card_item_remove.setOnClickListener {
            qtd--
            holder.itemView.cart_item_qtd.text = qtd.toString()
        }

        holder.itemView.card_item_delete.setOnClickListener {
            list.remove(item)
        }

    }
}