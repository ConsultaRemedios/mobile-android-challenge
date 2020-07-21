package com.benhurqs.banners.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.banners.R
import com.benhurqs.banners.adapters.BannerAdapter
import com.benhurqs.network.domain.repository.BannerRepository
import com.benhurqs.network.entities.Banner
import kotlinx.android.synthetic.main.banner_list_view_render.view.*

class BannerList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs){
    private lateinit var view: View
    init {
        initView()
    }

    private fun initView(){
        view = View.inflate(context, R.layout.banner_list_view_render, null)
        addView(view)
        callAPI()
    }

    private fun managerRecyclerView(list: List<Banner>?){
        val ll = object: LinearLayoutManager(context, HORIZONTAL, false){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (width/1.2).toInt()
                return true
            }
        }

        view.banner_list_recyclerview.layoutManager = ll
        view.banner_list_recyclerview.adapter = BannerAdapter(list){
            Log.e("click", "Banner item")
        }
    }

    private fun callAPI(){
        BannerRepository.getInstance().callBannerAPI(
            onStart = { onStart() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onStart() {
        Log.e("Banner", "Start")
        view.banner_list_progress.visibility = View.VISIBLE
    }

    private fun onSuccess(list: List<Banner>?) {
        Log.e("Banner", "Success")
        managerRecyclerView(list)
    }

    private fun onFailure(error: String?){
        Log.e("Banner", "Error")
    }

    private fun onFinish(){
        Log.e("Banner", "Finishing")
        view.banner_list_progress.visibility = View.GONE
    }
}