package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentReviewSearchAddressBinding
import com.zeepy.zeepyforandroid.review.view.adapter.ReviewSearchAddressAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration

//Todo: 삭제해도 되는 Fragment
class ReviewSearchAddressFragment : BaseFragment<FragmentReviewSearchAddressBinding>() {
    private lateinit var houseListAdapter: ReviewSearchAddressAdapter
    private val viewModel: WriteReviewViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewSearchAddressBinding {
        return FragmentReviewSearchAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        initRecyclerView()
        searchHouse()
        observeSearchResult()
    }

    private fun setToolbar() {
        binding.reviewToolbar.run {
            setTitle("리뷰작성")
            setBackButton {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun initRecyclerView() {
        houseListAdapter = ReviewSearchAddressAdapter()
        binding.recyclerviewResultHouse.run {
            adapter = houseListAdapter
            addItemDecoration(ItemDecoration(10,0))
        }
    }

    private fun searchHouse() {
        binding.buttonSearch.setOnClickListener {
        }
    }

    private fun observeSearchResult() {
        viewModel.houseListSearched.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()){
                houseListAdapter.submitList(viewModel.houseListSearched.value)
            } else {
                Toast.makeText(requireContext(), "결과가 없습니다. ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}