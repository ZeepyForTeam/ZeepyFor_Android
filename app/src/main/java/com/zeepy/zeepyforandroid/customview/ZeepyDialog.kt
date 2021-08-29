package com.zeepy.zeepyforandroid.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ZeepyDialogBinding
import com.zeepy.zeepyforandroid.mainframe.MainActivity

class ZeepyDialog(
    private val title: String,
    private val content: String?,
    private val leftButtonDrawable: Int?,
    private val leftButtonText: String? = "삭제",
    private val rightButtonDrawable: Int?,
    private val rightButtonText: String? = "취소",
    private val weightRightButton: Float? = 0.5f,
    private val weightLeftButton: Float? = 0.5f,
    private val theme: String? = null,
    private val singleButton: Boolean? = false,
    private val dialogClickListener: DialogClickListener?
) : DialogFragment() {
    private lateinit var binding: ZeepyDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ZeepyDialogBinding.inflate(inflater, container, false)
        initDialogText()
        setButtons()
        setSingleButton()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val deviceSize = (requireActivity() as MainActivity).getDeviceSize()
        setDialogSize(deviceSize[0], deviceSize[1])
        attachClickListener()
    }

    private fun setDialogSize(deviceWidth: Int, deviceHeight:Int) {
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.8).toInt()
        if(singleButton == true) {
            params?.height = (deviceHeight*0.235).toInt()
        }
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun initDialogText() {
        binding.textviewTitle.text = title
        binding.textviewLeftButton.text = leftButtonText
        binding.textviewRightButton.text = rightButtonText

        binding.textviewContent.run {
            if (content.isNullOrEmpty()) {
                visibility = View.GONE
            } else {
                text = content
                visibility = View.VISIBLE
            }
        }
        if (theme == "community") {
            binding.run {
                textviewLeftButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.zeepy_green_33))
                textviewRightButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.zeepy_white_f3))
            }
        } else if (theme == "myProfile"){
            binding.run {
                textviewLeftButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.zeepy_yellow_ee))
                textviewRightButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.zeepy_white_f3))
            }
        }
    }

    private fun setButtons() {
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

    private fun setSingleButton() {
        if (singleButton == true) {
            binding.linearlayoutButtonBothSide.visibility = View.GONE
            binding.textviewSinglebutton.visibility =View.VISIBLE
        }
    }

    private fun attachClickListener() {
        binding.textviewLeftButton.setOnClickListener {
            dialogClickListener?.clickLeftButton(this)
        }
        binding.textviewRightButton.setOnClickListener {
            dialogClickListener?.clickRightButton(this)
        }
        binding.textviewSinglebutton.setOnClickListener {
            dialogClickListener?.clickLeftButton(this)
        }
    }
}