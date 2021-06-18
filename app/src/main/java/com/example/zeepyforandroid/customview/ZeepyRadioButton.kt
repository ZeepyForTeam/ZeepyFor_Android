package com.example.zeepyforandroid.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

class ZeepyRadioButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatRadioButton(context, attributeSet, defStyleAttr) {
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
        textSize = TEXT_SIZE.toFloat()
        buttonDrawable = null
        textAlignment = TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER_VERTICAL

        setBackGround()
        changeTextColor()
        changeFontFamily()
        setOnClickListener {}
        setOnCheckedChangeListener()
    }

    private fun setOnCheckedChangeListener() {
        setOnCheckedChangeListener { _, _ ->
            changeFontFamily()
        }
    }

    private fun setBackGround() {
        setBackgroundResource(RadioButtonType.findButtonType(buttonType).background)
    }

    private fun changeTextColor() {
        setTextColor(ContextCompat.getColorStateList(context, RadioButtonType.findButtonType(buttonType).textColor))
    }

    private fun changeFontFamily() {
        when(buttonType) {
            RadioButtonType.REVIEW_FINAL.type -> changeFontFamily(R.font.nanum_square_round_regular, R.font.nanum_square_round_extrabold)
            else -> typeface = Typeface.create(ResourcesCompat.getFont(context, R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        }
    }

    companion object {
        private const val TEXT_SIZE = 14
    }

    private fun changeFontFamily(unselected: Int, selected: Int) {
        typeface = if(isChecked) {
            Typeface.create(ResourcesCompat.getFont(context, selected), Typeface.NORMAL)
        } else {
            Typeface.create(ResourcesCompat.getFont(context, unselected), Typeface.NORMAL)
        }
    }

    enum class RadioButtonType(val type:Int, val background:Int, val textColor: Int){
        DEFAULT_RADIO_BUTTON(1, R.drawable.selector_zeepy_chip, R.color.selector_deafult_rb_text),
        REVIEW_GOOD(2, R.drawable.selector_good,R.color.selector_review_choice),
        REVIEW_SOSO(3, R.drawable.selector_soso, R.color.selector_review_choice),
        REVIEW_BAD(4, R.drawable.selector_dislike, R.color.selector_review_choice),
        REVIEW_FINAL(5, R.drawable.selector_review_final, R.color.zeepy_black_3b),
        COMMUNITY_TAG(10, R.drawable.selector_community_tag, R.color.selector_community_tag_text);

        companion object {
            fun findButtonType(buttonType: Int): RadioButtonType {
                return values().find { it.type == buttonType }
                    ?: throw IllegalArgumentException("Button Type Error")
            }
        }
    }
}