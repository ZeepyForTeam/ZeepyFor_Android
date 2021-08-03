package com.example.zeepyforandroid.eunm

import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.application.ZeepyApplication
import java.lang.IllegalArgumentException

enum class CommunityTendency(@StringRes val tendency: Int) {
    BUSINESS(R.string.lessor_business),
    KIND(R.string.lessor_kind),
    GRAZE(R.string.lessor_graze),
    SOFTY(R.string.lessor_softy),
    BAD(R.string.lessor_bad);

    companion object {
        fun findTendency(tendency: Int): String {
            return values().find { it.tendency == tendency }?.name ?: throw IllegalArgumentException("Tendency Not Matched")
        }
    }
}