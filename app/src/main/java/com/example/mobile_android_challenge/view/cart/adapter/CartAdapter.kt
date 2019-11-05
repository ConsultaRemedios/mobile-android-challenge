package com.example.mobile_android_challenge.view.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_android_challenge.R
import com.example.mobile_android_challenge.model.ItemCart
import com.example.mobile_android_challenge.util.loadImage
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_game_adapter.view.*

class CartAdapter() : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var itemsCart: List<ItemCart>

    init {
        itemsCart = listOf()
    }

    private var onItemListener: (item: ItemCart) -> Unit = {}

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemListener(itemsCart[adapterPosition])
            }
        }

        fun render(item: ItemCart) {
            var spinnerArray = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            itemView.cartImgCover.loadImage(item.image)
            itemView.cartConsoleName.text = item.platform
            itemView.cartGameName.text = item.name
            itemView.cartGamePrice.text =
                itemView.context.getString(R.string.item_price, item.price.toString())
            itemView.cartGameAmount.adapter =
                ArrayAdapter(itemView.context,android.R.layout.simple_spinner_item, spinnerArray)
            itemView.cartGameAmount.setSelection(item.quantity - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemsCart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(itemsCart[position])
    }

    fun updateList() {
        notifyDataSetChanged()
    }

    fun update(items: List<ItemCart>) {
        this.itemsCart = emptyList()
        this.itemsCart = items
        updateList()
    }
}