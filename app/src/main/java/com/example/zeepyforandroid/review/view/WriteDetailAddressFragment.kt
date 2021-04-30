package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
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
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.text = getString(R.string.write_detail_address)
        val spannableText = notice?.text?.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText?.setSpan(CustomTypefaceSpan(typeface), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.tvAddress.text = viewModel.addressSelected.value

        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }

    private fun enableButton() {
        viewModel.detailAddress.observe(viewLifecycleOwner){
            if (!viewModel.checkEmptyDetailAddress()) {
                binding.btnNext.usableButton()
                goToCheckLessorPersonality()
            } else {
                binding.btnNext.unUseableButton()
            }
        }
    }

    private fun goToCheckLessorPersonality(){
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeDetailAddressFragment_to_lessorPersonalityFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        binding.etAddressDetail.text.clear()
    }
}