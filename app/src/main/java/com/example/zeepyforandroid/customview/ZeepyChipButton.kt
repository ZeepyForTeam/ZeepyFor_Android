package com.example.zeepyforandroid.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.fonts.FontFamily
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ViewZeepyChipButtonBinding
import com.example.zeepyforandroid.font
import com.google.android.material.chip.Chip

class ZeepyChipButton @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?= null, defStyleAttr:Int = 0): FrameLayout(context, attributeSet, defStyleAttr) {
    private val binding = ViewZeepyChipButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var background = -1
    private var chipName = ""
    private var buttonType = 1

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.zeepyChipButton,
            0,
            0
        )
        if (typedArray.hasValue(R.styleable.zeepyChipButton_chip_name)){
            chipName = typedArray.getString(R.styleable.zeepyChipButton_chip_name) ?: ""
        }
        if (typedArray.hasValue(R.styleable.zeepyChipButton_button_type)){
            buttonType = typedArray.getInt(R.styleable.zeepyChipButton_button_type, 1)
        }
        binding.tvChip.text = chipName
        check()
        setButtonType()
    }

    private fun check(){
        binding.root.setOnClickListener {
            isSelected = isSelected != true

            binding.tvChip.run {
                if (isSelected) {
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    setTextColor(ContextCompat.getColor(context, R.color.zeepy_black_3b))
                }
            }
        }
    }

    private fun setButtonType() {
        when(buttonType) {
            1 -> setBackgroundResource(R.drawable.selector_zeepy_chip)
            2 -> setBackgroundResource(R.drawable.selector_good)
            else -> setBackgroundResource(R.drawable.selector_zeepy_chip)
        }
    }
}