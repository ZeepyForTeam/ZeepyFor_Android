package com.zeepy.zeepyforandroid.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.zeepy.zeepyforandroid.databinding.ParticipationDialogBinding
import com.zeepy.zeepyforandroid.mainframe.MainActivity

class ParticipationDialog(val listener: ParticipationListener): DialogFragment() {
    private lateinit var binding: ParticipationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ParticipationDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickParticipationButton()
    }

    override fun onResume() {
        val deviceSize = (requireActivity() as MainActivity).getDeviceSize()
        setDialogSize(deviceSize[0], deviceSize[1])
        super.onResume()
    }

    private fun setDialogSize(deviceWidth: Int, deviceHeight:Int) {
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun clickParticipationButton() {
        binding.buttonPostComment.setOnClickListener{
            listener.participation(binding.edittextInformation.text.toString())
            dismiss()
        }
    }

    interface ParticipationListener{
        fun participation(participation: String)
    }
}