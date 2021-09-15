package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentReportBinding
import com.zeepy.zeepyforandroid.databinding.FragmentSettingsBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyProfileOptionsAdapter
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
            adapter = ReportOptionsAdapter(options, this@ReportFragment)
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
}