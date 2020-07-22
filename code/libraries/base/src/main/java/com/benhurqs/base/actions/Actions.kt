package com.benhurqs.base.actions

import android.content.Context
import android.content.Intent

object Actions {
    const val SPOTLIGHT_ID = "SPOTLIGHT_ID"

    fun searchSuggestionIntent(context: Context) =
        internalIntent(context, "com.benhurqs.suggestion.open")

    fun detailIntent(context: Context, id: Int?) =
        internalIntent(context, "com.benhurqs.detail.open").putExtra(SPOTLIGHT_ID, id)

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)
}