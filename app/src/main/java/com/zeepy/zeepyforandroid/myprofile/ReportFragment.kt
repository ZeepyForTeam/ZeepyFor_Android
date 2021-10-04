package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialog.Companion.COMMUNITY
import com.zeepy.zeepyforandroid.customview.ZeepyDialog.Companion.MY_PROFILE
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentReportBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.ReportOptionsAdapter

class ReportFragment: BaseFragment<FragmentReportBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReportBinding {
        return FragmentReportBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressed()
        setToolbar()
        setOptionsRecyclerView()
    }

    private fun setOptionsRecyclerView() {
        val options = arrayOf("자취방 후기 글이 아니에요.", "비방과 욕설을 사용했어요.", "허위사실을 기재했어요.", "사기 글이에요.", "기타 사유 선택")

        binding.rvOptionsList.apply {
            setHasFixedSize(true)
            adapter = ReportOptionsAdapter(options) {
                when (it) {
                    4 -> {
                        findNavController().navigate(R.id.action_reportFragment_to_reportOtherFragment)
                    }
                    else -> {
                        reportShowConfirmDialog()
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("신고하기")
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