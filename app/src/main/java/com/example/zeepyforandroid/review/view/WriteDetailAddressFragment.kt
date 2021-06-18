package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteDetailAddressBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ReviewNotice


class WriteDetailAddressFragment : BaseFragment<FragmentWriteDetailAddressBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteDetailAddressBinding {
        return FragmentWriteDetailAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        Log.e(viewModel.toString(), viewModel.toString())

        viewModel.changeCurrentFragment(ReviewNotice.WRITE_DETAIL_ADDRESS)
        initView()
        enableButton()
        goToCheckLessorPersonality()

    }

    private fun initView(){
        binding.layoutDetailAddress.tvAddress.text = viewModel.addressSearchQuery.value
        binding.layoutDetailAddress.btnNext.run {
            setText("다음으로")
        }
    }

    private fun enableButton() {
        viewModel.detailAddress.observe(viewLifecycleOwner){
//            if (!viewModel.checkEmptyDetailAddress()) {
//                binding.btnNext.usableButton()
//                goToCheckLessorPersonality()
//            } else {
//                binding.btnNext.unUseableButton()
//            }
        }
    }

    private fun goToCheckLessorPersonality(){
        binding.layoutDetailAddress.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeDetailAddressFragment_to_lessorPersonalityFragment)
        }
    }

    override fun onStop() {
        super.onStop()
//        viewModel.addressSearchQuery.value = null
        binding.layoutDetailAddress.etAddressDetail.text.clear()
    }
}