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
        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                if (!it[0].furnitures.isNullOrEmpty()) {
                    binding.tvCharacteristicsContent.text = args.buildingSummaryModel.reviews?.get(0)?.furnitures?.joinToString(separator = ", ") { furniture ->
                        resources.getString(Options.getOptionFromString(furniture))
                    }.toString()
                }
            }
        }
    }

    // FIXME: Consider using an adapter..
    private fun setPictures() {
        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                if (!it[0].imageUrls.isNullOrEmpty()) {
                    it[0].imageUrls.let { images ->
                        val imagesCount = images.size
                        if (!it.isNullOrEmpty()) {
                            when (imagesCount) {
                                1 -> {
                                    binding.image1.load(images[0])
                                }
                                2 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                }
                                3 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                    binding.image3.load(images[2])
                                }
                                4 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                    binding.image3.load(images[2])
                                    binding.imageAdd.load(images[3])
                                } else -> {
                                binding.image1.load(images[0])
                                binding.image2.load(images[1])
                                binding.image3.load(images[2])
                                binding.imageAdd.load(images[3])
                            }
                            }
                        }
                    }
                }
            }
        }
    }
}