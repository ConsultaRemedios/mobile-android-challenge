package br.com.angelorobson.templatemvi.view.shoppingcart

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.shoppingcart.widgets.ShoppingCardAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_shopping_cart.*


class ShoppingCardFragment : Fragment(R.layout.fragment_shopping_cart) {

    private val compositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

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
                                    adapter.submitList(model.shoppingCartResult.shoppingItemsCart)
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