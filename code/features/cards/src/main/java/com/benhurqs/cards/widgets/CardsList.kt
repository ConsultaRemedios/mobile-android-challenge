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
import com.benhurqs.cards.widgets.contract.CardsContracts
import com.benhurqs.cards.widgets.presenter.CardsPresenter
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Spotlight
import kotlinx.android.synthetic.main.cards_list_view_render.view.*

class CardsList(context: Context, attrs: AttributeSet): FrameLayout(context, attrs), CardsContracts.View{
    private lateinit var view: View
    init {
        initView()
    }

    private fun initView(){
        view = View.inflate(context, R.layout.cards_list_view_render, null)
        addView(view)

        val presenter = CardsPresenter(this)
        presenter.callAPI()
    }



    override fun loadCards(list: List<Spotlight>) {
        view.cards_list_recyclerview.adapter = CardsAdapter(list){ spotlight ->
            (context as Activity).startActivity(Actions.detailIntent(context, spotlight.id))
        }
    }

    override fun showLoading() {
        view.cards_list_progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view.cards_list_progress.visibility = View.GONE
    }

    override fun hideContent() {
        view.cards_list_recyclerview.visibility = View.GONE
    }
}