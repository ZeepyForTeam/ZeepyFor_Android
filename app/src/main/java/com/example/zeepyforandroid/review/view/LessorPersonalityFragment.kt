package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentLessorPersonalityBinding
import com.example.zeepyforandroid.eunm.CommunityTendency.Companion.findTendency
import com.example.zeepyforandroid.review.view.adapter.LessorPersonalityAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessorPersonalityFragment : BaseFragment<FragmentLessorPersonalityBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLessorPersonalityBinding {
        return FragmentLessorPersonalityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(viewModel.toString(), viewModel.toString())

        viewModel.changeCurrentFragment(ReviewNotice.CHECK_LESSOR_PERSONALITY)
        setNextButton()
        setLessorPersonalities()
        goToWriteDetailLessorInfo()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
        }
    }

    private fun setLessorPersonalities() {
        binding.rvLessorCommunication.run {
            adapter = LessorPersonalityAdapter(requireContext()){
                binding.btnNext.setUsableButton()
                viewModel.changeLessorPersonality(findTendency(it))
                viewModel.lessorPersonality.value?.let { it1 -> Log.e("tendency", it1) }
            }
            addItemDecoration(ItemDecoration(10,0))
        }
    }

    private fun goToWriteDetailLessorInfo() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_lessorPersonalityFragment_to_writeLessorInfoFragment)
        }
    }
}