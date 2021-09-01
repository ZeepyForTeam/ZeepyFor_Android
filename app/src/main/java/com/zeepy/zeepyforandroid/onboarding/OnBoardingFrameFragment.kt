package com.zeepy.zeepyforandroid.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentOnboardingFrameBinding
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFrameFragment: BaseFragment<FragmentOnboardingFrameBinding>(){
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingFrameBinding {
        return FragmentOnboardingFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIsAlreadyOnBoarded()
        setOnBoardingViewPagerAdapter()
        controlLottieSpeed()
    }

    private fun controlLottieSpeed() {
        binding.lottieAnimation.speed = 0.6f
    }

    private fun setOnBoardingViewPagerAdapter() {
        binding.viewpagerOnboarding.adapter = OnBoardingViewPagerAdapter(this)
        binding.dotsIndicator.setViewPager2(binding.viewpagerOnboarding)
    }

    private fun checkIsAlreadyOnBoarded() {
        if (userPreferenceManager.fetchUserOnBoard()) {
            findNavController().navigate(R.id.action_onBoardingFrameFragment_to_mainFrameFragment)
        }
    }
}