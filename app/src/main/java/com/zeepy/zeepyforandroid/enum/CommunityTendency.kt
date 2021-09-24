package com.zeepy.zeepyforandroid.enum

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class CommunityTendency(@StringRes val tendency: Int) {
    BUSINESS(R.string.lessor_business),
    KIND(R.string.lessor_kind),
    GRAZE(R.string.lessor_graze),
    SOFTY(R.string.lessor_softy),
    BAD(R.string.lessor_bad),
    ALL(R.string.filter_all_home);

    companion object {
        fun findTendency(tendency: Int): String {
            return values().find { it.tendency == tendency }?.name
                ?: throw IllegalArgumentException("Tendency Not Matched")
        }

        fun findTendencyFromString(text: String): Int {
            return values().find {
                it.name == text
            }?.tendency ?: throw IllegalArgumentException("String and Name Not Matched")
        }
    }
}