package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.zeepyforandroid.databinding.FragmentReviewSearchAddressBinding
import com.example.zeepyforandroid.review.adapter.ReviewSearchAddressAdapter
import com.example.zeepyforandroid.review.dto.ReviewSearchAddressModel
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.VerticalItemDecoration


class ReviewSearchAddressFragment : Fragment() {
    private lateinit var binding: FragmentReviewSearchAddressBinding
    private lateinit var houseListAdapter: ReviewSearchAddressAdapter
    private val viewModel: WriteReviewViewModel by activityViewModels()
    private val dummyHouseList = mutableListOf<ReviewSearchAddressModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewSearchAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        loadDummyData()
        initRecyclerView()
        searchHouse()
        deleteAll()
    }

    private fun setToolbar() {
        binding.reviewToolbar.setTitle("리뷰작성")
        binding.reviewToolbar.setBackButton {
            Toast.makeText(requireContext(), "success toolbar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        houseListAdapter = ReviewSearchAddressAdapter()
        binding.recyclerviewResultHouse.run {
            adapter = houseListAdapter
            addItemDecoration(VerticalItemDecoration(10))
        }
    }

    private fun searchHouse() {
        viewModel.houseListSearched.observe(viewLifecycleOwner, Observer {
            houseListAdapter.submitList(viewModel.houseListSearched.value)
        })
    }

    private fun deleteAll() {
        viewModel.searchQuery.observe(viewLifecycleOwner, Observer {
            if (viewModel.searchQuery.value == null) {
                binding.buttonDeleteAll.visibility = View.GONE
            } else {
                binding.buttonDeleteAll.visibility = View.VISIBLE
                Log.e("query", viewModel.searchQuery.value.toString())
            }
        })
    }

    private fun loadDummyData() {
        dummyHouseList.apply {
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "헤라 팰리스",
                    null,
                    "주단태 이녀석",
                    "시즌 3를 기대해주세요"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "정보처리기사",
                    null,
                    "실기시험",
                    "4월 24일"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "Zeepy 달려!",
                    null,
                    "안드로이드 뷰공장 가동중",
                    "running"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성"
                )
            )

        }
        viewModel.changeHouseListSearched(dummyHouseList)
    }
}