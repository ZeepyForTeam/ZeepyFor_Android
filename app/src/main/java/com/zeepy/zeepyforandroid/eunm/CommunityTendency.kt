package com.zeepy.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
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