package java.games.ecommerce.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImgCroped(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}

fun ImageView.loadImg(url: String) {
    Picasso
        .get()
        .load(url)
        .fit()
        .into(this)
}