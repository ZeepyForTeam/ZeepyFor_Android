package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteDetailAddressBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.CustomTypefaceSpan


class WriteDetailAddressFragment : BaseFragment<FragmentWriteDetailAddressBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()

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
        initView()
        enableButton()
    }

    private fun initView(){
        val spannableText = binding.tvWriteAddress.text.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText.setSpan(CustomTypefaceSpan(typeface), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.tvAddress.text = viewModel.addressSelected.value

        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                Navigation.findNavController(binding.root).popBackStack()
            }
        }

        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }

    private fun enableButton() {
        viewModel.detailAddress.observe(viewLifecycleOwner){
            if (!viewModel.checkEmptyDetailAddress()) {
                binding.btnNext.usableButton()
            } else {
                binding.btnNext.unUseableButton()
            }
        }
    }
}