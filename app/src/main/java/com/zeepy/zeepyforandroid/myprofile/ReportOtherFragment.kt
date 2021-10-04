package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentReportOtherBinding
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialog.Companion.MY_PROFILE
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.myprofile.viewmodel.MyProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

class ReportOtherFragment: BaseFragment<FragmentReportOtherBinding>() {
    var isReportReasonFilled = false
    var isDetailInfoFilled = false

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReportOtherBinding {
        return FragmentReportOtherBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressed()
        setSendButton()
        setButtonChangeListener()
    }

    private fun setSendButton() {
        binding.btnNext.apply {
            setText("보내기")
            setUnUsableButton()
            setOnClickListener {
                reportShowConfirmDialog()
            }
        }
    }

    private fun setButtonChangeListener() {
        binding.etReportReasonContent.doAfterTextChanged {
            isReportReasonFilled = it!!.isNotEmpty()

            if (isReportReasonFilled && isDetailInfoFilled)
                binding.btnNext.setCommunityUsableButton()
            else
                binding.btnNext.setUnUsableButton()
        }

        binding.etDetailInfoContent.doAfterTextChanged {
            isDetailInfoFilled = it!!.isNotEmpty()

            if (isReportReasonFilled && isDetailInfoFilled)
                binding.btnNext.setCommunityUsableButton()
            else
                binding.btnNext.setUnUsableButton()
        }
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    fun reportShowConfirmDialog() {
        val confirmDialog =
            ZeepyDialogBuilder(this.resources.getString(R.string.report_confirm), MY_PROFILE)

        confirmDialog.setLeftButton(R.drawable.box_grayf9_8dp, "취소")
            .setRightButton(R.drawable.box_green33_8dp, "확인했으며, 신고할게요")
            .setContent(this.resources.getString(R.string.report_confirm_content))
            .setButtonHorizontalWeight(0.287f, 0.712f)
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                    Toast.makeText(context, "신고하였습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            })
            .build()
            .show(this.childFragmentManager, this.tag)
    }
}