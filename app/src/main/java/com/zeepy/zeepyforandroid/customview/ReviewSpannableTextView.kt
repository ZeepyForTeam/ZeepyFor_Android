package com.zeepy.zeepyforandroid.customview

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan

class ReviewSpannableTextView @JvmOverloads constructor(context:Context, attributeSet: AttributeSet? = null, defStyle: Int = 0): AppCompatTextView(context, attributeSet, defStyle) {
    private val typeFace = Typeface.create(ResourcesCompat.getFont(context, R.font.nanum_square_round_extrabold), Typeface.NORMAL)
    private var spannableStart = -1
    private var spannableEnd = -1

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.zeepySpannableText,
            0,
            0
        )
        setTextColor(context.getColor(R.color.zeepy_blue_5f))
        textSize = 24f

        if (typedArray.hasValue(R.styleable.zeepySpannableText_community_type)) {
            if (typedArray.getBoolean(R.styleable.zeepySpannableText_community_type, false)) {
                changeToCommunityTheme()
            }
        }
        val spannableString = SpannableString(text)

        if(typedArray.hasValue(R.styleable.zeepySpannableText_start) && typedArray.hasValue(R.styleable.zeepySpannableText_end)) {
            spannableStart = typedArray.getInt(R.styleable.zeepySpannableText_start, 0)
            spannableEnd = typedArray.getInt(R.styleable.zeepySpannableText_end, 0)

            spannableString.setSpan(CustomTypefaceSpan(typeFace), spannableStart, spannableEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            text = spannableString
        }

        if (typedArray.hasValue(R.styleable.zeepySpannableText_second_start) && typedArray.hasValue(R.styleable.zeepySpannableText_second_end)) {
            spannableStart = typedArray.getInt(R.styleable.zeepySpannableText_second_start, 0)
            spannableEnd = typedArray.getInt(R.styleable.zeepySpannableText_second_end, 0)

            spannableString.setSpan(CustomTypefaceSpan(typeFace), spannableStart, spannableEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            text = spannableString
        }
    }

    private fun changeToCommunityTheme() {
        setTextColor(context.getColor(R.color.zeepy_green_33))
    }
}