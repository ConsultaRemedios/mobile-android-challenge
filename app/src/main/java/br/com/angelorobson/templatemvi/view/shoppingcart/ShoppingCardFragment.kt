package br.com.angelorobson.templatemvi.view.shoppingcart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.FragmentGameDetailBinding
import br.com.angelorobson.templatemvi.databinding.FragmentShoppingCartBinding
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.shoppingcart.widgets.ShoppingCardAdapter
import br.com.angelorobson.templatemvi.view.utils.BindingFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_shopping_cart.*


class ShoppingCardFragment : BindingFragment<FragmentShoppingCartBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_shopping_cart

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ShoppingCardAdapter()
        setupRecyclerView(adapter)

        val disposable = Observable.empty<ShoppingCartEvent>()
                .compose(getViewModel(ShoppingCartViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.shoppingCartResult) {
                                is ShoppingCartModelResult.Loading -> {

                                }
                                is ShoppingCartModelResult.ShoppingCartItemsLoaded -> {
                                    val result = model.shoppingCartResult
                                    binding.itemsSize = result.itemsSize
                                    binding.priceWithDiscount = result.totalWithDiscount
                                    binding.priceWithoutDiscount = result.totalWithoutDiscount
                                    binding.freteValue = result.freteValue
                                    adapter.submitList(result.shoppingItemsCart)
                                }
                            }
                        },
                        {

                        }
                )

        compositeDisposable.add(disposable)
    }

    private fun setupRecyclerView(shoppingCardAdapter: ShoppingCardAdapter) {
        shopping_cart_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shoppingCardAdapter
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}