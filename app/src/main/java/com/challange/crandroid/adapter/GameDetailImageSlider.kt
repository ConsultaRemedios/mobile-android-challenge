package com.challange.crandroid.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.challange.crandroid.R
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder

class GameDetailImageSlider(items: MutableList<String>) : PlutoAdapter<String, GameDetailImageSlider.ViewHolder>(items) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.layout_image_slider_item)
    }

    class ViewHolder(parent: ViewGroup, itemLayoutId: Int) : PlutoViewHolder<String>(parent, itemLayoutId) {
        private var imageView: ImageView = getView(R.id.game_image_slider)

        override fun set(item: String, position: Int) {
            Glide.with(context)
                .load(item)
                .into(imageView)
        }
    }
}