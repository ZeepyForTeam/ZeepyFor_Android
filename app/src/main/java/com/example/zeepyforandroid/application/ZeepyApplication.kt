package com.example.zeepyforandroid.application

import android.app.Application
import com.example.zeepyforandroid.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeepyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}