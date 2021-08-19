package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentWriteDetailAddressBinding
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
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

        binding.layoutDetailAddress.apply {
            this.textviewCityDistinct.text = args.selectedAddress.cityDistinct
            this.textviewPrimaryAddress.text = args.selectedAddress.primaryAddress
        }

        initView()
        goToCheckLessorPersonality()
        enableNextButton()
        hideDetailAddressBox()
    }

    private fun initView(){
        binding.layoutDetailAddress.btnNext.run {
            if(viewModel.isJustRegisterAddress.value == true) {
                setText("등록완료")
            } else {
                setText("다음으로")
            }
        }
    }

    private fun hideDetailAddressBox() {
        binding.layoutDetailAddress.etAddressDetail.run {
            visibility = if (viewModel.isJustRegisterAddress.value != true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun goToCheckLessorPersonality(){
        binding.layoutDetailAddress.btnNext.setOnClickListener {
            if (viewModel.isJustRegisterAddress.value == true) {
                viewModel.addAddress(args.selectedAddress.toAddressListDTO())
                requireParentFragment().requireParentFragment().findNavController().popBackStack()
            } else {
                val fullAddress = "${args.selectedAddress.cityDistinct} ${args.selectedAddress.primaryAddress} ${binding.layoutDetailAddress.etAddressDetail.text}"
                viewModel.changeFullReviewAddress(fullAddress)
                findNavController().navigate(R.id.action_writeDetailAddressFragment_to_lessorPersonalityFragment)
            }
        }
    }

    private fun enableNextButton() {
        binding.layoutDetailAddress.etAddressDetail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.layoutDetailAddress.btnNext.setUnUsableButton()
                } else {
                    binding.layoutDetailAddress.btnNext.setUsableButton()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        binding.layoutDetailAddress.etAddressDetail.text?.clear()
    }
}