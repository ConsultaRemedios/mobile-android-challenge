package com.benhurqs.cards.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.benhurqs.cards.R
import com.benhurqs.cards.adapter.CardsAdapter
import kotlinx.android.synthetic.main.cards_list_view_render.view.*

class CardsList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs){

    init {
        initView()
    }

    private fun initView(){
        val view = View.inflate(context, R.layout.cards_list_view_render, null)
        managerRecyclerView(view)
        addView(view)
    }

    private fun managerRecyclerView(view: View){
        view.cards_list_recyclerview.adapter = CardsAdapter(null){
            Log.e("click", "Banner item")
        }

    }


}