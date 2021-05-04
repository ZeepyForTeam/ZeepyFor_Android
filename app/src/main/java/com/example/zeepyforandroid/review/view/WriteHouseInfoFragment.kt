package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteHouseInfoBinding


class WriteHouseInfoFragment : BaseFragment<FragmentWriteHouseInfoBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteHouseInfoBinding {
        return FragmentWriteHouseInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNextButton()
        selectRadioSingleItem()
    }

    private fun setNextButton() {
        binding.btnNext.setText("다음으로")
    }

    private fun selectRadioSingleItem() {
        binding.groupFinalReview.setOnCheckedChangeListener { group, checkId ->
            when(checkId) {
                binding.btnGood.id -> {
                    binding.btnNoRecommendation.isChecked = false
                    binding.btnRecommendation.isChecked = false
                }
                binding.btnRecommendation.id -> {
                    binding.btnNoRecommendation.isChecked = false
                    binding.btnGood.isChecked = false
                }
                binding.btnNoRecommendation.id -> {
                    binding.btnGood.isChecked = false
                    binding.btnRecommendation.isChecked = false
                }
            }
        }
    }
}