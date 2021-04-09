package com.example.zeepyforandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeepyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}