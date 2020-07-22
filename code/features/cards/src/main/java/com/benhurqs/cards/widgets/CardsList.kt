package com.benhurqs.cards.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.benhurqs.base.actions.Actions
import com.benhurqs.cards.R
import com.benhurqs.cards.adapter.CardsAdapter
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Spotlight
import kotlinx.android.synthetic.main.cards_list_view_render.view.*

class CardsList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs){
    private lateinit var view: View
    init {
        initView()
        callAPI()
    }

    private fun initView(){
        view = View.inflate(context, R.layout.cards_list_view_render, null)
        addView(view)
    }

    private fun managerRecyclerView(list: List<Spotlight>?){
        view.cards_list_recyclerview.adapter = CardsAdapter(list){ spotlight ->
            (context as Activity).startActivity(Actions.detailIntent(context, spotlight.id))
        }

    }

    private fun callAPI(){
        NetworkRepository.getSpotlight(
            onStart = { onStart() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onStart() {
        Log.e("Cards", "Start")
        view.cards_list_progress.visibility = View.VISIBLE
    }

    private fun onSuccess(list: List<Spotlight>?) {
        Log.e("Cards", "Success")
        managerRecyclerView(list)
    }

    private fun onFailure(error: String?){
        Log.e("Cards", "Error")
    }

    private fun onFinish(){
        Log.e("Cards", "Finishing")
        view.cards_list_progress.visibility = View.GONE
    }


}