package com.benhurqs.base.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object ImageUtils {

    fun loadImage(imageView: ImageView, url: String?){
        if(url == null){
            return
        }

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
        Glide.with(imageView.context).load(url).apply(requestOptions).into(imageView)
    }
}