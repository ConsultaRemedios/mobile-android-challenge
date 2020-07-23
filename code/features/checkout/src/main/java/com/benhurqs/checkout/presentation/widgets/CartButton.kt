package com.benhurqs.checkout.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.benhurqs.checkout.R
import com.benhurqs.checkout.data.CartRepository
import com.benhurqs.network.entities.Spotlight
import kotlinx.android.synthetic.main.cart_button.view.*

class CartButton(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    private var spotlight: Spotlight? = null

    init {
        initView()
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.cart_button, null)
        addView(view)

        managerButtons()

    }

    private fun managerButtons(){
        rootView.cart_add.setOnClickListener {
            if(spotlight != null) {
                CartRepository.getInstance().addItem(spotlight!!)
            }

        }

        rootView.cart_remove.setOnClickListener {
            if(spotlight != null) {
                CartRepository.getInstance().removeItem(spotlight!!)
            }
        }


    }

    fun setSpotlight(spotlight: Spotlight){
        this.spotlight = spotlight
        if(CartRepository.getInstance().hasItem(spotlight.id)){
            rootView.cart_add.visibility = View.GONE
            rootView.cart_remove.visibility = View.VISIBLE
        }else{
            rootView.cart_add.visibility = View.VISIBLE
            rootView.cart_remove.visibility = View.GONE
        }
    }
}