package com.example.zeepyforandroid.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ViewZeepyButtonBinding

class ZeepyButton: ConstraintLayout {
    lateinit var binding: ViewZeepyButtonBinding
    private val _isActive = MutableLiveData<Boolean>(false)
    val isActive: LiveData<Boolean>
        get() = _isActive


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
        binding.root.clipToOutline = true
    }

    fun setText(string: String) {
        binding.tvButton.text = string
    }

    fun onClick(listener: OnClickListener){
        binding.root.setOnClickListener {
            listener.onClick(this)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUnUsableButton() {
        binding.root.setOnTouchListener { _, _ -> true }
        binding.layoutButton.setBackgroundResource(R.drawable.zeepy_button_unuseable)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_gray_ba))
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUsableButton() {
        binding.root.setOnTouchListener { _, _ -> false }
        binding.layoutButton.setBackgroundResource(R.drawable.zeepy_button)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_white_f3))
    }

    fun setParticipationButton() {
        binding.layoutButton.setBackgroundResource(R.drawable.zeepy_button_participation)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_green_33))
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setCommunityUsableButton() {
        binding.root.setOnTouchListener { _, _ -> false }
        binding.layoutButton.setBackgroundResource(R.drawable.zeepy_button_community)
        binding.tvButton.setTextColor(getColor(context, R.color.white))
    }

    fun changeIsActivie(isActive: Boolean) {
        this._isActive.value = isActive
    }
}