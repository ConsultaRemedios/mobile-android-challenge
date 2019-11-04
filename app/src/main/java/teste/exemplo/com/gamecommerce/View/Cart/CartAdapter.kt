package teste.exemplo.com.gamecommerce.View.Cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.annotations.NonNull
import teste.exemplo.com.gamecommerce.Model.Cart
import teste.exemplo.com.gamecommerce.Model.GameAddedToCart
import teste.exemplo.com.gamecommerce.Presenter.Cart.CartAdapterPresenter
import teste.exemplo.com.gamecommerce.R

import teste.exemplo.com.gamecommerce.Util.MoneyUtil
import teste.exemplo.com.gamecommerce.View.Main.MainActivity


class CartAdapter(private var context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>(),
    ICartAdapterView {

    lateinit var dataChanged: DataChangedResponse
    private lateinit var cartAdapterPresenter: CartAdapterPresenter
    private var viewHolder: ViewHolder? = null
    private lateinit var cartItem: GameAddedToCart

    interface DataChangedResponse {
        fun onDataChange()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        cartAdapterPresenter = CartAdapterPresenter(this)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Cart.items.size
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        viewHolder!!.setIsRecyclable(false)
        updateHolderViews(position)
    }

    override fun updateHolderViews(position: Int){
        if(Cart.items.size <= 0){
            (context as MainActivity).supportFragmentManager.popBackStack()
            return
        }
        cartItem = Cart.items[position]

        setHolderTexts()
        setHolderImage()
        setHolderClickListeners(position)
    }

    override fun setHolderTexts() {
        viewHolder!!.itemName.text = cartItem.game.name
        viewHolder!!.itemPrice.text = MoneyUtil.formatMoney(cartItem.game.price * cartItem.qty)
        viewHolder!!.quantity.text = cartItem.qty.toString()
    }

    override fun setHolderImage() {
        Glide.with(context).load(cartItem.game.image).into(viewHolder!!.itemImage)
    }

    override fun setHolderClickListeners(position: Int){
        viewHolder!!.addQuantity.setOnClickListener {
            cartAdapterPresenter.addCartQuantity(position)
        }
        viewHolder!!.removeQuantity.setOnClickListener {
            cartAdapterPresenter.removeCartQuantity(position)
        }
    }

    override fun updateCartQuantity(position: Int) {
        updateHolderViews(position)
    }

    override fun notifyDataHasChanged(){
        dataChanged.onDataChange()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val removeQuantity: AppCompatImageView = itemView.findViewById(R.id.remove_quantity)
        val addQuantity: AppCompatImageView = itemView.findViewById(R.id.add_quantity)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        var quantity: TextView = itemView.findViewById(R.id.quantity)
    }
}