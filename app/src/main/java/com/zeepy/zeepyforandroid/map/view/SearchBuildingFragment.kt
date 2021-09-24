package com.zeepy.zeepyforandroid.map.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentSearchBuildingBinding
import com.zeepy.zeepyforandroid.map.SearchBuildingAdapter
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.viewmodel.MapViewModel
import com.zeepy.zeepyforandroid.util.ext.showKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchBuildingFragment : BaseFragment<FragmentSearchBuildingBinding>() {
    private val viewModel: MapViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBuildingBinding {
        return FragmentSearchBuildingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.layoutSearchAddress.etSearchAddress.showKeyboard()
        setSearchBuildingAdapter()
        setSearchOnClickListener()
    }


    private fun setSearchBuildingAdapter() {
        binding.layoutSearchAddress.recyclerviewResult.adapter = SearchBuildingAdapter(object : SearchBuildingAdapter.SelectBuildingInterface{
            override fun selectAddress(building: BuildingModel) {
                // go back to map with this building selected and and have Zeepy Buildings nearby show up too
                viewModel.updatePlaceSelectedFromSearch(building)

                // popBackStack to Map Fragment
                findNavController().popBackStack()
            }
        })
        viewModel.resultSearchedBuildings.observe(viewLifecycleOwner) {
            (binding.layoutSearchAddress.recyclerviewResult.adapter as SearchBuildingAdapter).submitList(it)
        }
    }

    private fun setSearchOnClickListener() {
        binding.layoutSearchAddress.buttonSearch.setOnClickListener {

            viewModel.searchBuildingByKeyword(binding.layoutSearchAddress.etSearchAddress.text.toString())
            if (viewModel.resultSearchedBuildings.value.isNullOrEmpty()) {
                viewModel.searchBuildingByAddress(binding.layoutSearchAddress.etSearchAddress.text.toString())
            }

        }
    }
}