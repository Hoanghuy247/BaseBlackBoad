package com.example.baseblackboard.activity

import android.app.Application
import com.example.baseblackboard.utils.AppResources

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppResources.setAppContext(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
}