package br.com.weslleymaciel.gamesecommerce.view.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.weslleymaciel.gamesecommerce.MainActivity
import br.com.weslleymaciel.gamesecommerce.R
import br.com.weslleymaciel.gamesecommerce.common.models.CartItem
import br.com.weslleymaciel.gamesecommerce.common.utils.CartHelper
import br.com.weslleymaciel.gamesecommerce.common.utils.loadImage
import br.com.weslleymaciel.gamesecommerce.common.utils.numberToPrice
import org.jetbrains.anko.find
import java.lang.String

class CartAdapter(private val items: List<CartItem>, val mChangeListener: DataChangedListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_cart_item, parent, false), mChangeListener)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CartViewHolder(view: View, val mChangeListener: DataChangedListener) : RecyclerView.ViewHolder(view) {

        private val view = view
        private val ivImage = view.find<ImageView>(R.id.ivGame)
        private val ivDelete = view.find<ImageView>(R.id.ivDelete)
        private val ivAddItem = view.find<ImageView>(R.id.ivAddItem)
        private val ivRemoveItem = view.find<ImageView>(R.id.ivRemoveItem)
        private val tvTitle = view.find<TextView>(R.id.tvTitle)
        private val tvDiscount = view.find<TextView>(R.id.tvDiscount)
        private val tvPrice = view.find<TextView>(R.id.tvPrice)
        private val tvCounter = view.find<TextView>(R.id.tvCounter)

        fun bind(cartItem: CartItem) {
            ivImage.loadImage(cartItem.image!!, R.drawable.placeholder)
            tvTitle.text = cartItem.title
            tvCounter.text = cartItem.count.toString()

            ivAddItem.setOnClickListener {
                tvCounter.text = CartHelper.addCount(cartItem.game_id.toInt()).toString()
                mChangeListener.dataChanged(view, true)
            }

            ivRemoveItem.setOnClickListener {
                var count = CartHelper.removeCount(cartItem.game_id.toInt())
                if (count > 0){
                    tvCounter.text = count.toString()
                }else{
                    Toast.makeText(MainActivity.ctx, MainActivity.ctx!!.resources.getString(R.string.cart_item_removed), Toast.LENGTH_SHORT).show()
                }
                mChangeListener.dataChanged(view, true)
            }

            ivDelete.setOnClickListener {
                CartHelper.removeItemFromCart(cartItem.game_id.toInt())
                mChangeListener.dataChanged(view, true)
                Toast.makeText(MainActivity.ctx, MainActivity.ctx!!.resources.getString(R.string.cart_item_removed), Toast.LENGTH_SHORT).show()
            }

            if (cartItem.discount.toFloat() > 0.0){
                tvPrice.visibility = View.VISIBLE
                tvPrice.text = String.format(view.resources.getString(R.string.discount), numberToPrice(cartItem.price))
                tvDiscount.text = numberToPrice( cartItem.price.toFloat() - cartItem.discount.toFloat())
                tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                tvPrice.visibility = View.GONE
                tvDiscount.text = numberToPrice(cartItem.price)
            }

        }
    }

    interface DataChangedListener {
        fun dataChanged(view: View?, result: Boolean)
    }
}