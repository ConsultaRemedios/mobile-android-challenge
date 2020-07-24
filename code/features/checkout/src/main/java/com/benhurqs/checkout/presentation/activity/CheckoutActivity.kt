package com.benhurqs.checkout.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benhurqs.base.utils.Utils
import com.benhurqs.checkout.R
import com.benhurqs.checkout.presentation.adapter.CheckoutAdapter
import com.benhurqs.checkout.presentation.contracts.CheckoutContracts
import com.benhurqs.checkout.presentation.presenter.CheckoutPresenter
import com.benhurqs.network.entities.Cart
import kotlinx.android.synthetic.main.checkout_activity.*

class CheckoutActivity : AppCompatActivity(), CheckoutContracts.View{
    private var presenter: CheckoutContracts.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_activity)

        presenter = CheckoutPresenter(this)

    }

    override fun onResume() {
        super.onResume()
        presenter?.addObserver()

    }

    override fun onPause() {
        super.onPause()
        presenter?.removeObserver()
    }

    fun onClickBack(v: View?){
        this.finish()
    }

    fun onClickBuy(v: View){
        presenter?.callCheckoutAPI()
    }

    override fun sendMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isAdd() = !isFinishing

    override fun loadCart(list: ArrayList<Cart>) {
        cart_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cart_recyclerview.adapter = CheckoutAdapter(list)
    }

    override fun showLoading() {
        checkout_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        checkout_progress.visibility = View.GONE
    }

    override fun hideFreight() {
        checkout_freight_content.visibility = View.GONE
    }

    override fun showFreight(value: Double) {
        checkout_freight_content.visibility = View.VISIBLE
        checkout_total_freight_value.text = Utils.formatPrice(value)
    }

    override fun disableButton() {
        checkout_button.background = resources.getDrawable(R.drawable.gray_button)
        checkout_button.isClickable = false
    }

    override fun enableButton() {
        checkout_button.background = resources.getDrawable(R.drawable.green_button)
        checkout_button.isClickable = true
    }

    override fun hideButton() {
        checkout_button.visibility = View.GONE
    }

    override fun showButton() {
        checkout_button.visibility = View.VISIBLE
    }

    override fun updateCartQtd(qtd: Int, value: Double) {
        checkout_total_title.text = getString(R.string.produtos_qtd, qtd)
        checkout_total_value.text = Utils.formatPrice(value)
    }

    override fun updateTotal(value: Double) {
        checkout_total_price.text = Utils.formatPrice(value)

    }
}