package java.games.ecommerce.utils

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import java.math.BigDecimal
import java.text.DecimalFormat

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

fun BigDecimal?.asCurrency() = "R$%.2f".format(this ?: 0,0F)

