package com.benhurqs.banners.widgets.presenter

import com.benhurqs.banners.widgets.contracts.BannerContract
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Banner

class BannerPresenter(var view: BannerContract.View) : BannerContract.Presenter{


    override fun callAPI() {
        NetworkRepository.getBanners(
            onStart = { onStart() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onStart() {
        view.showLoading()
    }

    private fun onSuccess(list: List<Banner>?) {
        if(list.isNullOrEmpty()){
            view.hideContent()
        }else{
            view.loadBanner(list)
        }
    }

    private fun onFailure(error: String?){
        view.hideContent()
    }

    private fun onFinish(){
        view.hideLoading()
    }
}