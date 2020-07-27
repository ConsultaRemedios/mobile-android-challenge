package com.games.ecommerce.main.ui.activity.shoppingcart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.games.ecommerce.R
import com.games.ecommerce.main.data.model.ShoppingGame
import com.games.ecommerce.utils.asCurrency
import com.games.ecommerce.utils.asStrokeText
import com.games.ecommerce.utils.loadImgCroped
import kotlinx.android.synthetic.main.card_shoppingcart.view.*


class ShoppingCartAdapter(
    private val cartList: List<ShoppingGame>,
    private val onPlusClick: (ShoppingGame) -> Unit,
    private val onMinusClick: (ShoppingGame) -> Unit,
    private val onTrashClick: (ShoppingGame) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellsForRow = layoutInflater.inflate(R.layout.card_shoppingcart, parent, false)
        return ShoppingCartViewHolder(cellsForRow, onPlusClick, onMinusClick, onTrashClick)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ShoppingCartViewHolder).bind(cartList[position])
    }

}

class ShoppingCartViewHolder(
    private val view: View,
    private val onPlusClick: (ShoppingGame) -> Unit,
    private val onMinusClick: (ShoppingGame) -> Unit,
    private val onTrashClick: (ShoppingGame) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun bind(cart: ShoppingGame) {
        itemView.apply {
            title_shopping_card.text = cart.title
            price_shopping_card.text = cart.price.asCurrency()
            oldprice_shopping_card.text = ("de " + cart.originalPrice.asCurrency()).asStrokeText()
            image_shopping_card.loadImgCroped(cart.image)
            amount_shopping_card.text = cart.amount.toString()
            plus_btn_cart.setOnClickListener {
                onPlusClick(cart)
            }
            minus_btn_cart.setOnClickListener {
                onMinusClick(cart)
            }
            trash_shopping_card.setOnClickListener {
                onTrashClick(cart)
            }
        }
    }
}