package com.zeepy.zeepyforandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ViewZeepyToolbarBinding

class ZeepyToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int=0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var binding: ViewZeepyToolbarBinding =
        ViewZeepyToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(title: String) {
        binding.textviewToolbar.text = title
    }

    fun setBackButton(listener: OnClickListener) {
        this.binding.buttonBack.run {
            visibility = View.VISIBLE
            setImageResource(R.drawable.btn_back)
            setOnClickListener {
                listener.onClick(this@ZeepyToolbar)
            }
        }
    }

    fun clearButton() {
        this.binding.buttonBack.run {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }
    }

    fun setCommunityLocation() {
        binding.textviewToolbar.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.zeepy_black_3b
            )
        )
        val title = binding.textviewToolbar.layoutParams as ConstraintLayout.LayoutParams
        title.endToEnd = id
        title.marginStart = 32
        val arrowDown = ContextCompat.getDrawable(context, R.drawable.ic_btn_arrow_down)
        binding.textviewToolbar.apply {
            layoutParams = title
            setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDown, null)
            compoundDrawablePadding = 3
        }
    }

    fun setScrapButton(listener: OnClickListener) {
        binding.checkboxScrap.run {
            visibility = View.VISIBLE
            buttonDrawable = null
            setBackgroundResource(R.drawable.selector_heart)
            setOnClickListener {
                listener.onClick(this@ZeepyToolbar)
            }
        }
    }

    fun setRightButton(drawable: Int, listener: OnClickListener) {
        binding.buttonRight.apply {
            visibility = View.VISIBLE
            setBackgroundResource(drawable)
            setOnClickListener {
                listener.onClick(this@ZeepyToolbar)
            }
        }
    }

    fun setRightButton2(drawable: Int, listener: OnClickListener) {
        binding.buttonRight2.apply {
            visibility = View.VISIBLE
            setBackgroundResource(drawable)
            setOnClickListener {
                listener.onClick(this@ZeepyToolbar)
            }
        }
    }

    fun setRightButtonMargin(margin: Int) {
        val rightButton = binding.buttonRight.layoutParams as ConstraintLayout.LayoutParams
        rightButton.marginEnd = margin
    }

    fun setLookaroundBuildingTitle() {
        val title = binding.textviewToolbar.layoutParams as ConstraintLayout.LayoutParams
        title.endToEnd = id
        title.marginStart = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60F, context.resources.displayMetrics)
            .toInt()
        binding.textviewToolbar.apply {
            layoutParams = title
        }
    }
}