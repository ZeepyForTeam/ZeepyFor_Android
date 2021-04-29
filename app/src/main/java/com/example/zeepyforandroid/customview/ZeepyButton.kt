package com.example.zeepyforandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.zeepyforandroid.databinding.ViewZeepyButtonBinding

class ZeepyButton: ConstraintLayout {
    private lateinit var binding: ViewZeepyButtonBinding

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defSytleAttr: Int): super(context, attrs, defSytleAttr){
        init()
    }

    private fun init() {
        binding = ViewZeepyButtonBinding.inflate(LayoutInflater.from(context), this, true)
        isClickable = true
        isEnabled = true
    }

    fun setText(string: String) {
        binding.tvButton.text = string
    }

    fun onClick(listener: OnClickListener){
        binding.root.setOnClickListener {
            listener.onClick(this)
        }
    }
}