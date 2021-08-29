package com.zeepy.zeepyforandroid.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentOnboardingFrameBinding

class OnBoardingFrameFragment: BaseFragment<FragmentOnboardingFrameBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingFrameBinding {
        return FragmentOnboardingFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBoardingViewPagerAdapter()
    }

    private fun setOnBoardingViewPagerAdapter() {
        binding.viewpagerOnboarding.adapter = OnBoardingViewPagerAdapter(this)
        binding.dotsIndicator.setViewPager2(binding.viewpagerOnboarding)
    }
}