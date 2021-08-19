package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentWriteHouseInfoBinding
import com.zeepy.zeepyforandroid.enum.TotalEvaluation.Companion.findTotalEvaluation
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel

class WriteHouseInfoFragment : BaseFragment<FragmentWriteHouseInfoBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteHouseInfoBinding {
        return FragmentWriteHouseInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        setNextButton()
        selectTotalEvaluation()
        enableNextButton()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
            setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_writeHouseInfoFragment_to_housePictureFragment)

            }
        }
    }

    private fun selectTotalEvaluation() {
        binding.groupFinalReview.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.btnGood.id -> viewModel.changeHouseTotalEvaluation(findTotalEvaluation(R.string.total_good))
                binding.btnRecommendation.id -> viewModel.changeHouseTotalEvaluation(
                    findTotalEvaluation(R.string.total_recommendation)
                )
                binding.btnNoRecommendation.id -> viewModel.changeHouseTotalEvaluation(
                    findTotalEvaluation(R.string.total_no_recommendation)
                )
            }
        }
    }

    private fun enableNextButton() {
        viewModel.reviewOfHouse.observe(viewLifecycleOwner) {
            checkInputALL()
        }
        viewModel.houseTotalEvaluation.observe(viewLifecycleOwner) {
            checkInputALL()
        }
    }

    private fun checkInputALL() {
        if (viewModel.checkTotalHouseReviewEmpty()) {
            binding.btnNext.setUnUsableButton()
        } else {
            binding.btnNext.setUsableButton()
        }
    }
}