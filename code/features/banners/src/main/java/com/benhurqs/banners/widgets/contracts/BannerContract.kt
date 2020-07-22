package com.benhurqs.banners.widgets.contracts

import com.benhurqs.network.entities.Banner

interface BannerContract{
    interface View{
        fun loadBanner(list: List<Banner>)
        fun showLoading()
        fun hideLoading()
        fun hideContent()
    }

    interface Presenter{
        fun callAPI()
    }
}