package br.com.angelorobson.templatemvi.model.domains

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spotlight(
        val id: Int,
        val title: String,
        val publisher: String,
        val image: String,
        val discount: Double,
        val price: Double,
        val description: String,
        val rating: Float,
        val stars: Int,
        val reviews: Int

) : Parcelable