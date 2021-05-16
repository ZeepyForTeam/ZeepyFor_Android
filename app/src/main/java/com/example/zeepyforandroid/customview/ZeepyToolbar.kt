package com.example.zeepyforandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ViewZeepyToolbarBinding

class ZeepyToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int=0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ViewZeepyToolbarBinding =
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
        binding.textviewToolbar.layoutParams = title
    }
}