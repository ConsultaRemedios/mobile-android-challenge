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

class CheckoutItemsAdapter(
    context: Context,
    items: ArrayList<CartItem>,
    onQuantityTapListener: OnQuantityTapListener
) : RecyclerView.Adapter<CheckoutItemsAdapter.ViewHolder>() {

    private var mItems = items
    private val mContext = context
    private val mOnQuantityTapListener = onQuantityTapListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.checkout_item, parent, false)
        return ViewHolder(view, mOnQuantityTapListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = mItems[position]

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
        holder.quantity.text = mContext.getString(R.string.quantity_with_value, cartItem.quantidade)
    }

    class ViewHolder(itemView: View, private var onQuantityTapListener: OnQuantityTapListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val quantityView: View = itemView.findViewById(R.id.quantity_view)

        init {
//            itemView.setOnClickListener(this)
            this.quantityView.setOnClickListener(this)
        }

        var image: ImageView = itemView.findViewById(R.id.game_cover)
        var platform: TextView = itemView.findViewById(R.id.game_platform)
        var title: TextView = itemView.findViewById(R.id.game_title)
        var price: TextView = itemView.findViewById(R.id.price_tag)
        var quantity: TextView = itemView.findViewById(R.id.quantity)

        override fun onClick(v: View?) {
            onQuantityTapListener.onQuantityTap(adapterPosition)
        }
    }

    interface OnQuantityTapListener {
        fun onQuantityTap(position: Int)
    }
}