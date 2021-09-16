package com.zeepy.zeepyforandroid.enum

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class Preference(@StringRes val prefer: Int, @DrawableRes val drawableId: Int) {
    GOOD(R.string.review_good, R.drawable.icon_smile_s),
    PROPER(R.string.review_soso, R.drawable.icon_soso_s),
    BAD(R.string.review_bad, R.drawable.icon_angry_s);

    companion object{
        fun findPreference(prefer: Int): String {
            return values().find { it.prefer == prefer }?.name ?: throw IllegalArgumentException("Prefer Not Matched")
        }

        fun getDrawableIdFromString(text: String): Int {
            return values().find { it.name == text }?.drawableId ?: throw IllegalArgumentException("Image Not Matched")
        }
    }
}