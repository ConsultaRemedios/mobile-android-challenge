package java.games.ecommerce.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

val <T> LiveData<T>.asMutable: MutableLiveData<T>
    get() = this as? MutableLiveData<T>
        ?: throw IllegalStateException("$this isn't a valid MutableLiveData")

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { action(it) } })
}