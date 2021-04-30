package com.example.zeepyforandroid.util

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import javax.inject.Inject


class SpannableText @Inject constructor(
    val typeface: Typeface
){
    fun setSpan(spannable: SpannableString, start:Int, end: Int) {
        spannable.setSpan(CustomTypefaceSpan(typeface),start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

}