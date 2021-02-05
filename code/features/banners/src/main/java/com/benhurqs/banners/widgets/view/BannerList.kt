package com.benhurqs.banners.widgets.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.banners.R
import com.benhurqs.banners.adapters.BannerAdapter
import com.benhurqs.banners.widgets.contracts.BannerContract
import com.benhurqs.banners.widgets.presenter.BannerPresenter
import com.benhurqs.base.actions.Actions
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Banner
import kotlinx.android.synthetic.main.banner_list_view_render.view.*

class BannerList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs), BannerContract.View{
    private lateinit var view: View

    init {
        initView()
    }

    override fun loadBanner(list: List<Banner>) {
        val ll = object: LinearLayoutManager(context, HORIZONTAL, false){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (width/1.2).toInt()
                return true
            }
        }

        view.banner_list_recyclerview.layoutManager = ll
        view.banner_list_recyclerview.adapter = BannerAdapter(list){
            (context as Activity).startActivity(Actions.webviewIntent(context, it?.url))
        }
    }

    override fun showLoading() {
        view.banner_list_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view.banner_list_progress.visibility = View.GONE
    }

    override fun hideContent() {
        view.banner_list_recyclerview.visibility = View.GONE
    }

    private fun initView(){
        view = View.inflate(context, R.layout.banner_list_view_render, null)
        addView(view)

        val presenter = BannerPresenter(this)
        presenter.callAPI()
    }






}