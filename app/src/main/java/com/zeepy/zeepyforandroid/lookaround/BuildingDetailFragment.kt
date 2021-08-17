package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentBuildingDetailBinding
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
        binding.tvCharacteristicsContent.text = args.buildingSummaryModel.furnitures?.joinToString(separator = ", ") {
            resources.getString(it.option)
        }.toString()
    }
}