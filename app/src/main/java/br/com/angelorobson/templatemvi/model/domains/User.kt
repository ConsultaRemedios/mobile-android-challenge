package br.com.angelorobson.templatemvi.model.domains

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val login: String = "",
        val avatarUrl: String = ""
) : Parcelable