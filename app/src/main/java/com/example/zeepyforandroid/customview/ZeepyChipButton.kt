package com.example.zeepyforandroid.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ViewZeepyChipButtonBinding
import com.example.zeepyforandroid.font
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

class ZeepyChipButton @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?= null, defStyleAttr:Int = 0): AppCompatRadioButton(context, attributeSet, defStyleAttr) {
    var buttonType = DEFAULT_CHIP

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.zeepyChipButton,
            0,
            0
        )
        if (typedArray.hasValue(R.styleable.zeepyChipButton_button_type)){
            buttonType = typedArray.getInt(R.styleable.zeepyChipButton_button_type, 1)
        }
        typeface = Typeface.create(ResourcesCompat.getFont(context, R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        textSize = TEXT_SIZE.toFloat()
        buttonDrawable = null
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER_VERTICAL
        check()
        setButtonType()
        setOnCheckedChangeListener()
    }

    private fun check(){
        setOnClickListener {
            isSelected = isSelected != true

            text.run {
                if (isSelected) {
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    setTextColor(ContextCompat.getColor(context, R.color.zeepy_black_3b))
                }
            }
        }
    }

    private fun setOnCheckedChangeListener() {
        setOnCheckedChangeListener { btn, id ->
            if (isSelected) {
                setTextColor(ContextCompat.getColor(context, R.color.zeepy_black_3b))
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }

    private fun setButtonType() {
        when(buttonType) {
            DEFAULT_CHIP -> setBackgroundResource(R.drawable.selector_zeepy_chip)
            LIKE_CHIP -> setBackgroundResource(R.drawable.selector_good)
            SOSO_CHIP -> setBackgroundResource(R.drawable.selector_soso)
            DISLIKE_CHIP -> setBackgroundResource(R.drawable.selector_dislike)
            else -> setBackgroundResource(R.drawable.selector_zeepy_chip)
        }
    }


    companion object {
        private const val TEXT_SIZE = 14
        private const val DEFAULT_CHIP = 1
        private const val LIKE_CHIP = 2
        private const val SOSO_CHIP = 3
        private const val DISLIKE_CHIP = 4
    }
}