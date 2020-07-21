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
import kotlinx.android.synthetic.main.banner_list_view_render.view.*

class BannerList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs){

    init {
        initView()
    }

    private fun initView(){
        val view = View.inflate(context, R.layout.banner_list_view_render, null)
        managerRecyclerView(view)
        addView(view)
    }

    private fun managerRecyclerView(view: View){
        val ll = object: LinearLayoutManager(context, HORIZONTAL, false){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (width/1.2).toInt()
                return true
            }
        }

        view.banner_list_recyclerview.layoutManager = ll
        view.banner_list_recyclerview.adapter = BannerAdapter(null){
            Log.e("click", "Banner item")
        }

    }


}