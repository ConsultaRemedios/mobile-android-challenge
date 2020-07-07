package br.com.angelorobson.templatemvi.view.shoppingcart

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.angelorobson.templatemvi.R
import br.com.angelorobson.templatemvi.databinding.FragmentShoppingCartBinding
import br.com.angelorobson.templatemvi.model.domains.ShoppingCart
import br.com.angelorobson.templatemvi.view.getViewModel
import br.com.angelorobson.templatemvi.view.shoppingcart.widgets.ShoppingCardAdapter
import br.com.angelorobson.templatemvi.view.utils.BindingFragment
import br.com.angelorobson.templatemvi.view.utils.setVisibleOrGone
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_shopping_cart.*


class ShoppingCardFragment : BindingFragment<FragmentShoppingCartBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_shopping_cart

    private val compositeDisposable = CompositeDisposable()
    private var mList = listOf<ShoppingCart>()
    private var mTotalWithDiscount = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ShoppingCardAdapter()
        setupRecyclerView(adapter)
        val clearProductSubject = PublishSubject.create<ShoppingCart>()

        val disposable = Observable.mergeArray(
                adapter.addItemClicks.map { AddButtonItemClicked(it) },
                adapter.removeItemClicks.map { RemoveButtonItemClicked(it) },
                adapter.clearCartItemClicks.switchMap {
                    showConfirmDialog(it, clearProductSubject)
                    clearProductSubject.map {
                        ClearButtonItemClicked(it)
                    }
                },
                shopping_purchase_button.clicks().map {
                    game_search_progress.setVisibleOrGone(true)
                    ButtonPurchaseClickedEvent(mList, mTotalWithDiscount)
                }
        )
                .compose(getViewModel(ShoppingCartViewModel::class).init(InitialEvent))
                .subscribe(
                        { model ->
                            when (model.shoppingCartResult) {
                                is ShoppingCartModelResult.Loading -> {
                                    game_search_progress.setVisibleOrGone(true)
                                }
                                is ShoppingCartModelResult.ShoppingCartItemsLoaded -> {
                                    val result = model.shoppingCartResult
                                    mList = result.shoppingItemsCart
                                    mTotalWithDiscount = result.totalWithDiscount
                                    binding.isButtonPurchaseEnable = mList.isNotEmpty()

                                    binding.totalQuantity = result.totalQuantity
                                    binding.priceWithDiscount = mTotalWithDiscount
                                    binding.priceWithoutDiscount = result.totalWithoutDiscount
                                    binding.freteValue = result.freteValue
                                    adapter.submitList(mList)
                                    game_search_progress.setVisibleOrGone(false)
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

    private fun showConfirmDialog(shoppingCart: ShoppingCart, clearProductSubject: PublishSubject<ShoppingCart>) {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)

        builder
                .setTitle(R.string.clear_product)
                .setMessage(getString(R.string.product_clear_warning_msg, shoppingCart.spotlight.title))
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    dialog.dismiss()
                    clearProductSubject.onNext(shoppingCart)
                }
        builder.setNeutralButton("Cancelar") { dialog, id -> }

        val alert = builder.create()
        alert.show()
    }

}

fun ImageButton.enable() {
    this.isEnabled = true
    this.imageAlpha = 0xFF
}

fun ImageButton.disable() {
    this.isEnabled = false
    this.imageAlpha = 0x3F
}