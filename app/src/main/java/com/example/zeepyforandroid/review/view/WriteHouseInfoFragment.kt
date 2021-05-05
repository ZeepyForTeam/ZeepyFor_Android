package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteHouseInfoBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ReviewNotice


class WriteHouseInfoFragment : BaseFragment<FragmentWriteHouseInfoBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteHouseInfoBinding {
        return FragmentWriteHouseInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.changeCurrentFragment(ReviewNotice.CHECK_HOUSE_CONDITION)

        setNextButton()
        selectRadioSingleItem()
        goToLoadHousePicture()
    }

    private fun setNextButton() {
        binding.btnNext.setText("다음으로")
    }

    private fun goToLoadHousePicture() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeHouseInfoFragment_to_housePictureFragment)
        }
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