package com.benhurqs.cards.widgets.presenter

import com.benhurqs.cards.widgets.contract.CardsContracts
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Spotlight

class CardsPresenter(private val view: CardsContracts.View): CardsContracts.Presenter{
    override fun callAPI() {
        NetworkRepository.getSpotlight(
            onStart = { onStart() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onStart() {
        view.showLoading()
    }

    private fun onSuccess(list: List<Spotlight>?) {
        if(list.isNullOrEmpty()){
            view.hideContent()
        }else{
            view.loadCards(list)
        }
    }

    private fun onFailure(error: String?){
        view.hideContent()
    }

    private fun onFinish(){
        view.hideLoading()
    }
}