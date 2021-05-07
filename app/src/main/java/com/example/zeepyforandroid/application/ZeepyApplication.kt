package com.example.zeepyforandroid.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class ZeepyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }


}