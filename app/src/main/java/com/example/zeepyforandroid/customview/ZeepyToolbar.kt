package com.example.zeepyforandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ViewZeepyToolbarBinding

class ZeepyToolbar : ConstraintLayout {
    private lateinit var binding: ViewZeepyToolbarBinding

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        binding = ViewZeepyToolbarBinding.inflate(LayoutInflater.from(context),this,true)

    }

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

    fun clearButton(){
        this.binding.buttonBack.run {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }
    }
}