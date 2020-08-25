package com.games.ecommerce.utils

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
fun AppCompatActivity.showFragment(fragment: Fragment, into: Int, push: Boolean = true, animIn: Int? = android.R.anim.fade_in, animOut: Int? = android.R.anim.fade_out, tag: String? = null) {
    if (push) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .setCustomAnimations(
                animIn ?: 0,
                animOut ?: 0)
            .replace(into, fragment)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                animIn ?: 0,
                animOut ?: 0)
            .replace(into, fragment)
            .commit()
    }
}

fun Double?.asCurrency() = "R$%.2f".format(this ?: 0,0F)

fun String.asStrokeText() : SpannableString {
    val content1 = this
    val spannableString1 = SpannableString(content1)
    spannableString1.setSpan(StrikethroughSpan(),0,content1.length,0)
    return spannableString1
}

