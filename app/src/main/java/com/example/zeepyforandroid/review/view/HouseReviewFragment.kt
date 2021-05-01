package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHouseReviewBinding
import com.example.zeepyforandroid.review.view.adapter.ReviewChoiceAdapter
import com.example.zeepyforandroid.review.view.adapter.ReviewOptionAdapter
import com.example.zeepyforandroid.util.ItemDecoration


class HouseReviewFragment : BaseFragment<FragmentHouseReviewBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHouseReviewBinding {
        return FragmentHouseReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReviewChoice()
        setRoomTypeChoice()
        setOptionChoice()
        setNextButton()
    }

    private fun setReviewChoice() {
        binding.rvReviewChoice.run {
            adapter = ReviewChoiceAdapter()
            addItemDecoration(ItemDecoration(13, 0))
        }
    }

    private fun setOptionChoice() {
        binding.rvOption.run {
            adapter = ReviewOptionAdapter()
            addItemDecoration(ItemDecoration(8, 8))
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            usableButton()
        }
    }

    private fun setRoomTypeChoice() {
        binding.roomtypeGroup.setOnCheckedChangeListener { group, checkId ->
            when (checkId) {
                binding.radiobtnOneRoom.id -> {
                    binding.radiobtnTwoRoom.isSelected = false
                    binding.radiobtnThreeRoom.isSelected = false
                }
                binding.radiobtnTwoRoom.id -> {
                    binding.radiobtnOneRoom.isSelected = false
                    binding.radiobtnThreeRoom.isSelected = false
                }
                binding.radiobtnThreeRoom.id -> {
                    binding.radiobtnTwoRoom.isSelected = false
                    binding.radiobtnOneRoom.isSelected = false
                }
            }
        }
    }
}