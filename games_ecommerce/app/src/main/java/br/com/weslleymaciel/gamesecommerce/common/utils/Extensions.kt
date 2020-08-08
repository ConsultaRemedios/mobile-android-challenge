package br.com.weslleymaciel.gamesecommerce.common.utils

import android.content.Context
import android.widget.ImageView
import br.com.weslleymaciel.gamesecommerce.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

fun ImageView.loadImage(url: String, placeHolder: Int) {
    Glide.with(this.context)
        .load(url)
        .placeholder(placeHolder)
        .centerCrop()
        .into(this)
}