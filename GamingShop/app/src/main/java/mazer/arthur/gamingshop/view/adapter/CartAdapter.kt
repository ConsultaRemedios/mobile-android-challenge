package mazer.arthur.gamingshop.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mazer.arthur.gamingshop.R
import mazer.arthur.gamingshop.data.remote.entities.Cart
import mazer.arthur.gamingshop.utils.extensions.inflate

class CartAdapter: RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var context: Context? = null
    var cartList: ArrayList<Cart> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(inflate(R.layout.item_cart,parent))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val cart = cartList[position]

        holder.title.text = cart.gameTitle
        Picasso.get().load(cart.gameImg).into(holder.gamePoster)

        holder.price.text = context?.getString(R.string.original_price_placeholder,  cart.price.toString())
        holder.discount.text =  context?.getString(R.string.discount_price_placeholder, cart.discount.toString())
        holder.price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var gamePoster: ImageView = view.findViewById(R.id.ivGamePoster)
        var title: TextView = view.findViewById(R.id.tvTitleGame)
        var price: TextView = view.findViewById(R.id.tvOriginalPrice)
        var discount: TextView = view.findViewById(R.id.tvDiscountedPrice)

    }

}