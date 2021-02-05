package com.benhurqs.search.searchbar.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.benhurqs.base.actions.Actions
import com.benhurqs.search.R
import kotlinx.android.synthetic.main.search_bar_view_render.view.*

class SearchBar(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    init {
        initView()
    }

    private fun initView(){
        val view = View.inflate(context, R.layout.search_bar_view_render, null)
        view.setOnClickListener { clickBar() }
        addView(view)
    }

    private fun clickBar(){
        (context as Activity).startActivity(Actions.searchSuggestionIntent(context))
    }
}