package com.example.zeepyforandroid.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

class ZeepyCheckBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : CompoundButton(context, attributeSet, defStyle) {
    var buttonType = -1

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.zeepyCompoundButton,
            0,
            0
        )
        if (typedArray.hasValue(R.styleable.zeepyCompoundButton_button_type)) {
            buttonType = typedArray.getInt(R.styleable.zeepyCompoundButton_button_type, 1)
        }
        buttonDrawable = null
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER_VERTICAL
        setOnClickListener { }
        attachCheckedChangeLister()
        changeTextColor()
        setBackground()
        setFontFamily()
    }

    private fun attachCheckedChangeLister() {
        setOnCheckedChangeListener { _, _ ->
            changeTextColor()
        }
    }

    private fun setBackground() {
        setBackgroundResource(CheckBoxType.findCheckBoxType(buttonType).background)
    }

    private fun setFontFamily() {
        typeface = Typeface.create(
            ResourcesCompat.getFont(context, R.font.nanum_square_round_extrabold),
            Typeface.NORMAL
        )
    }

    private fun changeTextColor() {
        setTextColor(
            CheckBoxType.findCheckBoxType(buttonType).textColor?.let {
                ContextCompat.getColorStateList(
                    context,
                    it
                )
            }
        )
    }

    enum class CheckBoxType(val buttonType: Int, val background: Int, val textColor: Int?) {
        DEFAULT_CHECK_BOX(1, R.drawable.selector_zeepy_chip, R.color.selector_default_rb_text),
        SECRET_COMMENT_CHECK_BOX(2, R.drawable.selector_checkbox_comment, R.color.selector_default_rb_text),
        BLUE_CHECKBOX(3, R.drawable.selector_blue_checkbox, R.color.selector_default_rb_text);


        companion object {
            fun findCheckBoxType(buttonType: Int): CheckBoxType {
                return values().find { it.buttonType == buttonType }
                    ?: throw IllegalArgumentException("Zeepy Check Box Type Error: ${buttonType}")
            }
        }
    }
}


