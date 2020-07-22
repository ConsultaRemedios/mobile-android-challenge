package com.benhurqs.detail.activity

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.benhurqs.base.actions.Actions.SPOTLIGHT_ID
import com.benhurqs.base.utils.ImageUtils
import com.benhurqs.base.utils.Utils
import com.benhurqs.detail.R
import com.benhurqs.network.domain.repository.NetworkRepository
import com.benhurqs.network.entities.Spotlight
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
    }

    override fun onStart() {
        super.onStart()
        getSpotlightID()
    }

    private fun getSpotlightID(){
        if(intent.hasExtra(SPOTLIGHT_ID)){
            callAPI(intent.extras?.getInt(SPOTLIGHT_ID))
        }else{
            this.finish()
        }
    }

    private fun callAPI(id: Int?){
        NetworkRepository.getDetail(
            spotlightID  = id,
            onStart = { onLoading() },
            onSuccess = { onSuccess(it) },
            onFailure = { onFailure(it) },
            onFinish = { onFinish() }
        )
    }

    private fun onLoading() {
        detail_spotlight_content.visibility = View.GONE
        detail_spotlight_progress.visibility = View.VISIBLE
    }

    private fun onSuccess(detail: Spotlight?) {
        if(detail == null) return

        ImageUtils.loadImage(detail_spotlight_img, detail.image)
        detail_spotlight_title.text = detail.title
        detail_spotlight_description.text = detail.description
        detail_spotlight_rating_num.text = getString(R.string.reviews, detail.reviews)
        detail_spotlight_rating_value.text = detail.rating.toString()


        detail_spotlight_last_price.paintFlags = detail_spotlight_last_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        detail_spotlight_price.text = Utils.formatPrice(detail.price - detail.discount)
        detail_spotlight_last_price.text = Utils.formatPrice(detail.price)

    }

    private fun onFailure(error: String?){
        //TODO tratar error
    }

    private fun onFinish(){
        detail_spotlight_content.visibility = View.VISIBLE
        detail_spotlight_progress.visibility = View.GONE
    }

    fun onClickBack(v: View?){
        this.finish()
    }

}