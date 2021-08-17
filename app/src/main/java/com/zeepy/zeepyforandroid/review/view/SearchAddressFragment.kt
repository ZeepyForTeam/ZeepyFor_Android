package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentSearchAddressBinding
import com.zeepy.zeepyforandroid.review.view.adapter.SearchAddressAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressFragment : BaseFragment<FragmentSearchAddressBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchAddressBinding {
        return FragmentSearchAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        Log.e(viewModel.toString(), viewModel.toString())

        goToWriteDetailAddress()
        attachSearchAddressAdapter()
        searchBuildingAddress()
    }

    private fun attachSearchAddressAdapter() {
        binding.layoutSearchAddress.recyclerviewResult.adapter = SearchAddressAdapter()
        viewModel.resultSearchedAddress.observe(viewLifecycleOwner) {
            (binding.layoutSearchAddress.recyclerviewResult.adapter as SearchAddressAdapter).submitList(it)
        }
    }

    private fun searchBuildingAddress() {
        binding.layoutSearchAddress.buttonSearch.setOnClickListener {
            Log.e("query", "${binding.layoutSearchAddress.etSearchAddress.text}")
            viewModel.searchBuildingAddress(binding.layoutSearchAddress.etSearchAddress.text.toString())
        }
    }

    private fun goToWriteDetailAddress() {
//        binding.layoutSearchAddress.btnNext.setOnClickListener{
//            viewModel.changeAddressSearchgQuery(binding.layoutSearchAddress.etSearchAddress.text.toString())
//            Navigation.findNavController(binding.root).navigate(R.id.action_searchAddressFragment_to_writeDetailAddressFragment)
//        }
    }
}