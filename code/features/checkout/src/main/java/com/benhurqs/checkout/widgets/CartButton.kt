package com.benhurqs.checkout.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.benhurqs.base.actions.Actions
import com.benhurqs.checkout.R

class CartButton(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    init {
        initView()
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.cart_button, null)
        view.setOnClickListener { clickButton() }
        addView(view)
    }

    private fun clickButton(){
        (context as Activity).startActivity(Actions.checkoutIntent(context))
    }
}