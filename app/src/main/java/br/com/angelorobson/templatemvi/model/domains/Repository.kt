package br.com.angelorobson.templatemvi.model.domains

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
        val id: Int,
        val name: String,
        val description: String,
        val forksCount: Int,
        val stargazersCount: Int,
        val user: User
) : Parcelable