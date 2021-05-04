package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentReviewSearchAddressBinding
import com.example.zeepyforandroid.review.view.adapter.ReviewSearchAddressAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration


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
            //Todo: LiveData null check 분기처리 확실하게 하고 ui 다시 수정하기
            if(!it.isNullOrEmpty()){
                houseListAdapter.submitList(viewModel.houseListSearched.value)
            } else {
                Toast.makeText(requireContext(), "invalid ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}