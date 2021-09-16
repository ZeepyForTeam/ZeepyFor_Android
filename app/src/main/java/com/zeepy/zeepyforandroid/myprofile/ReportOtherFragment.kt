package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentReportOtherBinding
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController


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
                reportShowConfirmDialog(this@ReportOtherFragment)
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
}