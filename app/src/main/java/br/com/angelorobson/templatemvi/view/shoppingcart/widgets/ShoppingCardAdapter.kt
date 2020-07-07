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
import br.com.angelorobson.templatemvi.view.utils.DiffUtilCallback
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

class ShoppingCardAdapter : ListAdapter<ShoppingCart, ShoppingCartViewHolder>(DiffUtilCallback<ShoppingCart>()) {

    private val addItemSubject = PublishSubject.create<Int>()
    val addItemClicks: Observable<ShoppingCart> = addItemSubject.map { position -> getItem(position) }

    private val removeItemSubject = PublishSubject.create<Int>()
    val removeItemClicks: Observable<ShoppingCart> = removeItemSubject.map { position -> getItem(position) }

    private val clearCartItemSubject = PublishSubject.create<Int>()
    val clearCartItemClicks: Observable<ShoppingCart> = clearCartItemSubject.map { position -> getItem(position) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val binding = DataBindingUtil.bind<ShoppingCardItemBinding>(
                LayoutInflater.from(parent.context).inflate(
                        viewType,
                        parent,
                        false
                )
        )

        return ShoppingCartViewHolder(binding?.root!!, binding, addItemSubject, removeItemSubject, clearCartItemClicks)
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
        private val gameClicksSubject: PublishSubject<Int>,
        private val removeItemSubject: PublishSubject<Int>,
        private val clearCartItemClicks: Observable<ShoppingCart>
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(shoppingCart: ShoppingCart) {
        binding?.apply {
            item = shoppingCart
            executePendingBindings()
        }
    }

}