package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentBuildingDetailBinding
import com.zeepy.zeepyforandroid.enum.Options
import com.zeepy.zeepyforandroid.lookaround.viewmodel.BuildingDetailViewModel

class BuildingDetailFragment: BaseFragment<FragmentBuildingDetailBinding>() {
    private val viewModel by viewModels<BuildingDetailViewModel>()
    private val args: BuildingDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changeSummary(args.buildingSummaryModel)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBuildingDetailBinding {
        return FragmentBuildingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setOptions()
        setPictures()

        binding.btnShowAllReviews.setOnClickListener {
            val action = BuildingDetailFragmentDirections.actionBuildingDetailFragmentToBuildingAllReviewsFragment(
                args.buildingSummaryModel
            )
            findNavController().navigate(action)
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("금성토성빌")
            setLookaroundBuildingTitle()
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }


    private fun setOptions() {
        binding.tvCharacteristicsContent.text = args.buildingSummaryModel.reviews?.get(0)?.furnitures?.joinToString(separator = ", ") {
            resources.getString(Options.getOptionFromString(it))
        }.toString()
    }

    private fun setPictures() {
        binding.image1.load(args.buildingSummaryModel.reviews?.get(0)?.imageUrls?.get(0))
        binding.image2.load(args.buildingSummaryModel.reviews?.get(0)?.imageUrls?.get(1))
        binding.image1.load(args.buildingSummaryModel.reviews?.get(0)?.imageUrls?.get(2))
        binding.imageAdd.load(args.buildingSummaryModel.reviews?.get(0)?.imageUrls?.get(3))
    }
}