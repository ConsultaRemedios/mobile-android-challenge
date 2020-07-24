package com.benhurqs.checkout.presentation.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.benhurqs.base.actions.Actions
import com.benhurqs.checkout.R
import com.benhurqs.checkout.data.CartRepository
import kotlinx.android.synthetic.main.cart_qtd_button.view.*

class CartQtdButton (context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    init {
        initView()
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.cart_qtd_button, null)
        view.setOnClickListener { clickButton() }
        addView(view)


    }

    private fun clickButton(){
        (context as Activity).startActivity(Actions.checkoutIntent(context))
    }

    fun update() {
        if(CartRepository.getInstance().getList().isNullOrEmpty()){
            rootView.cart_qtd.visibility = View.GONE
        }else{
            rootView.cart_qtd.visibility = View.VISIBLE
            rootView.cart_qtd.text = CartRepository.getInstance().getList().count().toString()
        }
    }
}