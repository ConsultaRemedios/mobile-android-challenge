package br.com.angelorobson.templatemvi.view.shoppingcart.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.ShoppingCardItemBinding
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.view.shoppingcart.disable
import br.com.angelorobson.templatemvi.view.shoppingcart.enable
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

class ShoppingCardAdapter : ListAdapter<ShoppingCart, ShoppingCartViewHolder>(DiffUtilCallback<ShoppingCart>()) {

    private val addItemSubject = PublishSubject.create<ShoppingCart>()
    val addItemClicks: Observable<ShoppingCart> = addItemSubject
            .map { shoppingCart -> shoppingCart }

    private val removeItemSubject = PublishSubject.create<ShoppingCart>()
    val removeItemClicks: Observable<ShoppingCart> = removeItemSubject.map { shoppingCart -> shoppingCart }

    private val clearCartItemSubject = PublishSubject.create<ShoppingCart>()
    val clearCartItemClicks: Observable<ShoppingCart> = clearCartItemSubject.map { shoppingCart -> shoppingCart }

    private val imageGameSubject = PublishSubject.create<ShoppingCart>()
    val imageGameClicks: Observable<ShoppingCart> = imageGameSubject.map { shoppingCart -> shoppingCart }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val binding = DataBindingUtil.bind<ShoppingCardItemBinding>(
                LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                )
        )

        return ShoppingCartViewHolder(binding?.root!!, binding, addItemSubject, removeItemSubject, clearCartItemSubject, imageGameSubject)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.shopping_card_item
    }

}

class ShoppingCartViewHolder(
        override val containerView: View,
        private val binding: ShoppingCardItemBinding?,
        private val addItemClicksSubject: PublishSubject<ShoppingCart>,
        private val removeItemSubject: PublishSubject<ShoppingCart>,
        private val clearCartItemSubject: PublishSubject<ShoppingCart>,
        private val imageGameSubject: PublishSubject<ShoppingCart>
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(shoppingCart: ShoppingCart) {
        binding?.apply {
            item = shoppingCart
            if (shoppingCart.quantity < 2) shoppingCartRemoveItemImageButton.disable() else shoppingCartRemoveItemImageButton.enable()

            shoppingCartAddItemImageButton.clicks().map { shoppingCart }
                    .subscribe(addItemClicksSubject)

            shoppingCartRemoveItemImageButton.clicks()
                    .map { shoppingCart }.subscribe(removeItemSubject)
            shoppingCartClearCart.clicks().map { shoppingCart }.subscribe(clearCartItemSubject)

            shoppingCartGameImageView.clicks().map { shoppingCart }.subscribe(imageGameSubject)

            executePendingBindings()
        }
    }

}