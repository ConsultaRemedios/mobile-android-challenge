package mazer.arthur.gamingshop.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun inflate(layoutId: Int, parent: ViewGroup?): View {
    return LayoutInflater.from(parent?.context).inflate(layoutId, parent, false)
}