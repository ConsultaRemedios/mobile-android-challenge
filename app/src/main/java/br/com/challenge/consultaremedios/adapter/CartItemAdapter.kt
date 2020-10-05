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
import br.com.challenge.consultaremedios.db.entity.CartItem
import br.com.challenge.consultaremedios.utils.GenericUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CartItemAdapter(
    context: Context,
    cartItems: List<CartItem>,
    private val gameQuantityAdapterListener: GameQuantityAdapterListener
) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private val mContext = context
    var items: List<CartItem> = cartItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_item_cart_item, parent, false)
        return ViewHolder(view, gameQuantityAdapterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = items[position]

        val requestOptions = RequestOptions.placeholderOf(R.drawable.game_cover_placeholder)
        Glide.with(mContext)
            .load(game.boxArtUrl)
            .apply(requestOptions)
            .into(holder.boxArt)

        holder.title.apply { text = game.title }
        holder.quantity.apply { text = game.quantity.toString() }
        holder.price.apply {
            text = GenericUtils.brazilianNumberFormat().format(game.unitPrice)
            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        holder.priceWithDiscount.apply { text = GenericUtils.brazilianNumberFormat().format(game.unitPriceWithDiscount) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(
        itemView: View,
        gameQuantityAdapterListener: GameQuantityAdapterListener
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.findViewById<ImageView>(R.id.remove_one).setOnClickListener {
                gameQuantityAdapterListener.onRemoveQuantityListener(adapterPosition)
            }
            itemView.findViewById<ImageView>(R.id.add_one).setOnClickListener {
                gameQuantityAdapterListener.onAddQuantityListener(adapterPosition)
            }
            itemView.findViewById<ImageView>(R.id.remove_game).setOnClickListener {
                gameQuantityAdapterListener.onRemoveGameListener(adapterPosition)
            }
        }

        val boxArt: ImageView = itemView.findViewById(R.id.boxart)
        val title: TextView = itemView.findViewById(R.id.title)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val price: TextView = itemView.findViewById(R.id.price)
        val priceWithDiscount: TextView = itemView.findViewById(R.id.price_with_discount)
    }

    interface GameQuantityAdapterListener {
        fun onRemoveQuantityListener(position: Int)
        fun onAddQuantityListener(position: Int)
        fun onRemoveGameListener(position: Int)
    }
}