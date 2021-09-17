package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundBuildingAllReviewsBinding
import com.zeepy.zeepyforandroid.lookaround.adapter.BuildingAllReviewsAdapter
import com.zeepy.zeepyforandroid.map.viewmodel.MapViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration


class BuildingAllReviewsFragment: BaseFragment<FragmentLookaroundBuildingAllReviewsBinding>() {
    private val args: BuildingAllReviewsFragmentArgs by navArgs()
    private val viewModel: MapViewModel by activityViewModels()
    private lateinit var reviewsAdapter: BuildingAllReviewsAdapter

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

        if (viewModel.buildingSelected.value != null) {
            reviewsAdapter.submitList(viewModel.buildingSelected.value?.reviews)
        } else {
            reviewsAdapter.submitList(args.buildingSummaryModel.toBuildingModel().reviews)
        }
    }

    private fun initRecyclerView() {
        binding.rvReviewList.apply {
            reviewsAdapter = BuildingAllReviewsAdapter {
                val action = BuildingAllReviewsFragmentDirections.actionBuildingAllReviewsFragmentToDetailedReviewFragment(
                    it
                )
                findNavController().navigate(action)
            }
            adapter = reviewsAdapter
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