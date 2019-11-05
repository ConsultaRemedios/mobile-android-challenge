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
import com.challange.crandroid.data.CartItem
import com.challange.crandroid.utils.GenericUtils.Companion.brazilianNumberFormat

class CheckoutItensAdapter(context: Context, itens: ArrayList<CartItem>) : RecyclerView.Adapter<CheckoutItensAdapter.ViewHolder>() {

    private var mItens = itens
    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkout_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItens.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = mItens[position]

        val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
        Glide.with(mContext)
            .load(cartItem.game.image)
            .apply(requestOptions)
            .into(holder.image)

//        val porterDuffColorFilter = PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.colorBlue), PorterDuff.Mode.DST_OVER)
//        holder.image.colorFilter = porterDuffColorFilter

        holder.platform.text = cartItem.game.platform
        holder.title.text = cartItem.game.title
        holder.price.text = brazilianNumberFormat().format(cartItem.precoSomaQuantidade)
        holder.quantity.text = "${cartItem.quantidade}"
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.game_cover)
        var platform: TextView = itemView.findViewById(R.id.game_platform)
        var title: TextView = itemView.findViewById(R.id.game_title)
        var price: TextView = itemView.findViewById(R.id.price_tag)
        var quantity: TextView = itemView.findViewById(R.id.quantity)
    }
}