package com.damcoassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageSearchApplication : Application() {
    companion object {
        lateinit var instance: ImageSearchApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}