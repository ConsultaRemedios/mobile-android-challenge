package teste.exemplo.com.gamecommerce.View.Cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.annotations.NonNull
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Model.GameAddedToCart
import teste.exemplo.com.gamecommerce.R
import teste.exemplo.com.gamecommerce.Util.Cache
import teste.exemplo.com.gamecommerce.Util.MoneyUtil

class CartAdapter(var context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>(),
    ICartAdapterView {
    private var viewHolder: ViewHolder? = null
    private lateinit var cartItem: GameAddedToCart

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Cart.items.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        viewHolder!!.setIsRecyclable(false)
        cartItem = Cart.items[position]
        setHolderTexts()
        setImage()
    }

    override fun setHolderTexts() {
        viewHolder!!.item_name.text = cartItem.game.name
        viewHolder!!.item_price.text = MoneyUtil.formatMoney(cartItem.game.price)
        viewHolder!!.quantity.text = cartItem.qty.toString()
    }

    override fun setImage() {
        Glide.with(context).load(cartItem.game.image).into(viewHolder!!.item_image)
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item_image: ImageView
        val item_name: TextView
        val item_price: TextView
        var quantity: TextView

        init {
            item_image = itemView.findViewById(R.id.item_image)
            item_name = itemView.findViewById(R.id.item_name)
            item_price = itemView.findViewById(R.id.item_price)
            quantity = itemView.findViewById(R.id.quantity)
        }
    }
}