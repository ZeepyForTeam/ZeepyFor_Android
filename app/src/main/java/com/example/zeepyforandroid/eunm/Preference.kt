package com.example.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class Preference(@StringRes val prefer: Int) {
    GOOD(R.string.review_good),
    PROPER(R.string.review_soso),
    BAD(R.string.review_bad);

    fun findPrefer(prefer: Int): Int {
        return values().find { it.prefer == prefer }?.prefer ?: throw IllegalArgumentException("Prefer Not Matched")
    }
}