package com.zeepy.zeepyforandroid.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ZeepyDialogBinding

class ZeepyDialog(
    private val title: String,
    private val content: String?,
    private val leftButtonDrawable: Int?,
    private val leftButtonText: String? = "삭제",
    private val rightButtonDrawable: Int?,
    private val rightButtonText: String? = "취소",
    private val weightRightButton: Float? = 0.5f,
    private val weightLeftButton: Float? = 0.5f,
    private val reverseTextColor: Boolean? = false
) : DialogFragment() {
    private lateinit var binding: ZeepyDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ZeepyDialogBinding.inflate(inflater, container, false)
        initView()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        getDeviceSize()
    }

    //defaultDisplay Deprecated로 인한 Version 처리
    private fun getDeviceSize() {
        var deviceWidth = 0
        var deviceHeight = 0
        val outMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = requireActivity().display
            display?.getRealMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
            setDialogSize(deviceWidth, deviceHeight)
        } else {
            @Suppress("DEPRECATION")
            val display = requireActivity().windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
            setDialogSize(deviceWidth, deviceHeight)
        }
    }

    private fun setDialogSize(deviceWidth: Int, deviceHeight:Int) {
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    private fun initView() {
        binding.textviewTitle.text = title
        binding.textviewLeftButton.text = leftButtonText
        binding.textviewRightButton.text = rightButtonText

        if (content.isNullOrEmpty()) {
            binding.textviewContent.visibility = View.GONE
        } else {
            binding.textviewContent.run {
                text = content
                visibility = View.VISIBLE
            }
        }

        if (reverseTextColor == true) {
            binding.run {
                textviewLeftButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textviewRightButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }

        if (leftButtonDrawable != null) {
            binding.textviewLeftButton.run {
                background = ContextCompat.getDrawable(requireContext(), leftButtonDrawable)
                if (weightLeftButton != null) {
                    val layoutParams = this.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = weightLeftButton
                }
            }
        }

        if (rightButtonDrawable != null) {
            binding.textviewRightButton.run {
                background = ContextCompat.getDrawable(requireContext(), rightButtonDrawable)
                if (weightRightButton != null) {
                    val layoutParams = this.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = weightRightButton
                }
            }
        }
    }

}