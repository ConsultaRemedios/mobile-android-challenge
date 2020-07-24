package com.benhurqs.checkout.presentation.contracts

import com.benhurqs.network.entities.Cart

interface CheckoutContracts {
    interface View{
        fun loadCart(list: ArrayList<Cart>)
        fun showLoading()
        fun hideLoading()

        fun hideFreight()
        fun showFreight(value: Double)

        fun disableButton()
        fun enableButton()
        fun hideButton()
        fun showButton()

        fun updateCartQtd(qtd: Int, value: Double)
        fun updateTotal(value: Double)

        fun sendMessage(msg: String)
        fun isAdd(): Boolean
        fun finish()
    }

    interface Presenter{
        fun callCheckoutAPI()
        fun addObserver()
        fun removeObserver()
    }
}