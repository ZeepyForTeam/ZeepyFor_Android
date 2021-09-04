package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundBuildingAllReviewsBinding
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.util.ItemDecoration


class BuildingAllReviewsFragment: BaseFragment<FragmentLookaroundBuildingAllReviewsBinding>() {
    private val args: BuildingAllReviewsFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundBuildingAllReviewsBinding {
        return FragmentLookaroundBuildingAllReviewsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvReviewList.apply {
            adapter = BuildingAllReviewsAdapter {
                val action = BuildingAllReviewsFragmentDirections.actionBuildingAllReviewsFragmentToDetailedReviewFragment(
                    it
                )
                findNavController().navigate(action)
            }
            addItemDecoration(ItemDecoration(10, 0))
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("건물리뷰")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }
}