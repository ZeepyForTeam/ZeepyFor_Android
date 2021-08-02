package com.example.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class Preference(@StringRes val prefer: Int) {
    GOOD(R.string.review_good),
    PROPER(R.string.review_soso),
    BAD(R.string.review_bad);

    companion object{
        fun findPreference(prefer: Int): String {
            return values().find { it.prefer == prefer }?.name ?: throw IllegalArgumentException("Prefer Not Matched")
        }
    }
}