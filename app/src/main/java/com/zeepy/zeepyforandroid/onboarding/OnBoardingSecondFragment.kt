package com.zeepy.zeepyforandroid.onboarding

import android.os.Bundle
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentOnBoardingSecondBinding

class OnBoardingSecondFragment: BaseFragment<FragmentOnBoardingSecondBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingSecondBinding {
        return FragmentOnBoardingSecondBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySpannableText()
    }

    private fun applySpannableText() {
        val ment = binding.textviewMent.text.toSpannable()
        val span = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zeepy_blue_5f))
        ment.setSpan(span, 6, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textviewMent.text = ment
    }
}