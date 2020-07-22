package com.benhurqs.base.actions

import android.content.Context
import android.content.Intent

object Actions {
    const val SPOTLIGHT_ID = "SPOTLIGHT_ID"
    const val BANNER_URL = "BANNER_URL"

    fun searchSuggestionIntent(context: Context) =
        internalIntent(context, "com.benhurqs.suggestion.open")

    fun detailIntent(context: Context, id: Int?) =
        internalIntent(context, "com.benhurqs.detail.open").putExtra(SPOTLIGHT_ID, id)

    fun webviewIntent(context: Context, url: String?) =
        internalIntent(context, "com.benhurqs.webview.open").putExtra(BANNER_URL, url)

    private fun internalIntent(context: Context, action: String) =
        Intent(action).setPackage(context.packageName)
}