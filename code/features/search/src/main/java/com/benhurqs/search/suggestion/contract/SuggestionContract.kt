package com.benhurqs.search.suggestion.contract

import com.benhurqs.network.entities.Suggestion

interface SuggestionContract {
    interface View{
        fun loadSuggestion(list: List<Suggestion>)
        fun showLoading()
        fun hideLoading()
        fun hideContent()
        fun showContent()
    }

    interface Presenter{
        fun callAPI(query: String?)
    }
}