package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteHouseInfoBinding
import com.example.zeepyforandroid.eunm.TotalEvaluation.Companion.findTotalEvaluation
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ReviewNotice

class WriteHouseInfoFragment : BaseFragment<FragmentWriteHouseInfoBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})
  
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
        goToLoadHousePicture()
        selectTotalEvaluation()
    }

    private fun setNextButton() {
        binding.btnNext.setText("다음으로")
    }

    private fun goToLoadHousePicture() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeHouseInfoFragment_to_housePictureFragment)
        }
    }

    private fun selectTotalEvaluation() {
        binding.groupFinalReview.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId) {
                    binding.btnGood.id -> viewModel.changeHouseTotalEvaluation(findTotalEvaluation(R.string.total_good))
                    binding.btnRecommendation.id -> viewModel.changeHouseTotalEvaluation(findTotalEvaluation(R.string.total_recommendation))
                    binding.btnNoRecommendation.id -> viewModel.changeHouseTotalEvaluation(findTotalEvaluation(R.string.total_no_recommendation))
                }

                Log.e("total evaluation", viewModel.houseTotalEvaluation.value.toString())
            }
        })
    }
}