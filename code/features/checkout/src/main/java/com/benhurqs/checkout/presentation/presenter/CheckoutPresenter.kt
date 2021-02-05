package com.benhurqs.checkout.presentation.presenter

import com.benhurqs.checkout.data.CartRepository
import com.benhurqs.checkout.data.CartUpdateListener
import com.benhurqs.checkout.presentation.contracts.CheckoutContracts
import com.benhurqs.network.domain.repository.NetworkRepository

class CheckoutPresenter(private val view: CheckoutContracts.View) : CheckoutContracts.Presenter, CartUpdateListener {
    init {
        view.loadCart(CartRepository.getInstance().getList())
    }

    override fun callCheckoutAPI() {
        NetworkRepository.checkout(
            onStart = {onLoading()},
            onSuccess = {onSuccess()},
            onFailure = {onFailure(it)},
            onFinish = {onFinish()}
        )
    }

    override fun addObserver() {
        CartRepository.getInstance().addObserver(this)
        onUpdate()
    }

    override fun removeObserver() {
        CartRepository.getInstance().removeObserver(this)
    }

    private fun onLoading(){
        if(view.isAdd()) {
            view.showLoading()
            view.hideButton()
        }
    }

    private fun onSuccess(){
        if(view.isAdd()) {
            CartRepository.getInstance().clearCart()
            view.sendMessage("Compra finalizada com sucesso")
            view.finish()
        }
    }

    private fun onFailure(error: String?){
        if(view.isAdd()) {
            view.sendMessage("Error ao finalizar compra. Tente novamente!")
        }
    }

    private fun onFinish(){
        if(view.isAdd()) {
            view.hideLoading()
            view.showButton()
        }
    }

    override fun onUpdate() {
        if(!view.isAdd()) {
            return
        }

        view.updateCartQtd(CartRepository.getInstance().getList().count(), CartRepository.getInstance().total)

        if(CartRepository.getInstance().freight == CartRepository.FREIGHT_FREE){
            view.hideFreight()
        }else{
            view.showFreight(CartRepository.getInstance().freight)
        }

        view.updateTotal(CartRepository.getInstance().freight + CartRepository.getInstance().total)

        if(CartRepository.getInstance().getList().count() > 0 ){
            view.enableButton()
        }else{
            view.disableButton()
        }
    }
}