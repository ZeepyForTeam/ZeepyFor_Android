package com.zeepy.zeepyforandroid.enum

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class CommunicationTendencySimple(@StringRes val tendency: Int) {
    BUSINESS(R.string.tendency_business),
    KIND(R.string.tendency_kind),
    GRAZE(R.string.tendency_graze),
    SOFTY(R.string.tendency_softy),
    BAD(R.string.tendency_bad);

    companion object {
        fun findTendencyStringFromId(tendency: Int): String {
            return values().find { it.tendency == tendency }?.name
                ?: throw IllegalArgumentException("Tendency Not Matched")
        }

        fun findTendencyIdFromString(text: String): Int {
            return values().find {
                it.name == text
            }?.tendency ?: throw IllegalArgumentException("String and Name Not Matched")
        }
    }
}