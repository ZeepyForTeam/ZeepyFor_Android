package com.example.zeepyforandroid.application

import android.app.Application
import android.content.Context
import com.example.zeepyforandroid.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeepyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        context = this
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

    companion object {
        var context:Context? = null
    }
}