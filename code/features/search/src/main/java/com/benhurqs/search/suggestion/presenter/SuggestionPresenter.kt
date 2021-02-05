package com.benhurqs.search.suggestion.presenter

import com.benhurqs.base.utils.Utils
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Suggestion
import com.benhurqs.search.suggestion.contract.SuggestionContract

class SuggestionPresenter(private val view: SuggestionContract.View) : SuggestionContract.Presenter{
    override fun callAPI(query: String?) {
        if(Utils.isEmpty(query)){
            view.hideContent()
            return
        }

        NetworkRepository.getSuggestion(
            query = query,
            onStart = { onLoading() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onLoading(){
        view.showLoading()
    }

    private fun onSuccess(list: List<Suggestion>?){
        if(list.isNullOrEmpty()){
            view.hideContent()
        }else{
            view.showContent()
            view.loadSuggestion(list)
        }
    }

    private fun onFailure(error: String?){
        view.hideContent()
    }

    private fun onFinish(){
        view.hideLoading()
    }


}