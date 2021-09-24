package com.zeepy.zeepyforandroid.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ViewZeepyButtonBinding

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
        binding.linearlayoutButton.setBackgroundResource(R.drawable.zeepy_button_unuseable)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_gray_ba))
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUsableButton() {
        binding.root.setOnTouchListener { _, _ -> false }
        binding.linearlayoutButton.setBackgroundResource(R.drawable.zeepy_button)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_white_f3))
    }

    fun setParticipationButton() {
        binding.linearlayoutButton.setBackgroundResource(R.drawable.zeepy_button_participation)
        binding.tvButton.setTextColor(getColor(context, R.color.zeepy_green_33))
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setCommunityUsableButton() {
        binding.root.setOnTouchListener { _, _ -> false }
        binding.linearlayoutButton.setBackgroundResource(R.drawable.zeepy_button_community)
        binding.tvButton.setTextColor(getColor(context, R.color.white))
    }

    fun changeIsActivie(isActive: Boolean) {
        this._isActive.value = isActive
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setMyProfileUsableButtion() {
        binding.root.setOnTouchListener { _, _ -> false }
        binding.linearlayoutButton.setBackgroundResource(R.drawable.zeepy_button_myprofile)
        binding.tvButton.setTextColor(getColor(context, R.color.white))
    }
}