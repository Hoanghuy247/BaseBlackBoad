package com.example.baseblackboard.utils

import android.content.Context

object AppResources {

    private var sAppContext: Context? = null

    fun setAppContext(context: Context) {
        if (sAppContext == null) {
            sAppContext = context
        }
    }

    fun getAppContext(): Context? = sAppContext
}