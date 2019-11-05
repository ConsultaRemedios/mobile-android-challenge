package com.example.mobile_android_challenge.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun ImageView.loadImage(imageUrl: String, circle: Boolean = false, errorPlaceholder: Int? = null) {

    val requestOptions = RequestOptions().apply {
        if (circle) circleCrop()
        if (errorPlaceholder != null) placeholder(errorPlaceholder)
    }

    Glide.with(context.applicationContext)
        .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}