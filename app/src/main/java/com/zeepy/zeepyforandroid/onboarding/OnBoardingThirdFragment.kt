package com.zeepy.zeepyforandroid.onboarding

import android.os.Bundle
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentOnBoardingThirdBinding
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingThirdFragment : BaseFragment<FragmentOnBoardingThirdBinding>() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingThirdBinding {
        return FragmentOnBoardingThirdBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySpannableText()
        finishOnBoarding()
    }

    private fun applySpannableText() {
        val ment = binding.textviewMent.text.toSpannable()
        val span = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zeepy_blue_5f))
        ment.setSpan(span, 11, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textviewMent.text = ment
    }

    private fun finishOnBoarding() {
        binding.buttonFinishOnboarding.setOnClickListener{
            userPreferenceManager.saveOnboard(true)
            findNavController().navigate(R.id.action_onBoardingFrameFragment_to_mainFrameFragment)
        }
    }
}