package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentWriteDetailAddressBinding
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ReviewNotice


class WriteDetailAddressFragment : BaseFragment<FragmentWriteDetailAddressBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})
    private val args: WriteDetailAddressFragmentArgs by navArgs()

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
        if (args != null) {
            binding.layoutDetailAddress.apply {
                this.textviewCityDistinct.text = args.selectedAddress.cityDistinct
                this.textviewPrimaryAddress.text = args.selectedAddress.primaryAddress
            }
        }

        initView()
        goToCheckLessorPersonality()

    }

    private fun initView(){
        binding.layoutDetailAddress.btnNext.run {
            setText("다음으로")
        }
    }

    private fun goToCheckLessorPersonality(){
        binding.layoutDetailAddress.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeDetailAddressFragment_to_lessorPersonalityFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        binding.layoutDetailAddress.etAddressDetail.text.clear()
    }
}