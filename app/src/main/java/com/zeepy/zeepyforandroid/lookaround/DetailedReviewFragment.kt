package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundDetailedReviewBinding


class DetailedReviewFragment: BaseFragment<FragmentLookaroundDetailedReviewBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundDetailedReviewBinding {
        return FragmentLookaroundDetailedReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setLookaroundBuildingTitle()
            setTitle("금성토성빌 상세리뷰")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }


}