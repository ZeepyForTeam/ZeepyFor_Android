package com.zeepy.zeepyforandroid.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

class OnBoardingViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OnBoardingFirstFragment()
            1-> OnBoardingSecondFragment()
            2-> OnBoardingThirdFragment()
            else -> throw IllegalArgumentException("error")
        }
    }

    override fun getItemCount() = 3
}