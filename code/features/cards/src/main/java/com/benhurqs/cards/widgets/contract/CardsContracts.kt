package com.benhurqs.cards.widgets.contract

import com.benhurqs.network.entities.Spotlight

interface CardsContracts{
    interface View{
        fun loadCards(list: List<Spotlight>)
        fun showLoading()
        fun hideLoading()
        fun hideContent()
    }

    interface Presenter{
        fun callAPI()
    }
}