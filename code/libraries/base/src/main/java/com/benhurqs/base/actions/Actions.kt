package com.benhurqs.base.actions

import android.content.Context
import android.content.Intent

object Actions {

    fun searchSuggestionIntent(context: Context) = internalIntent(context, "com.benhurqs.suggestion.open")

    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)
}