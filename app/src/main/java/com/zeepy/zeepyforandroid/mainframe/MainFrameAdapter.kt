package com.zeepy.zeepyforandroid.mainframe

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zeepy.zeepyforandroid.community.CommunityFrameFragment
import com.zeepy.zeepyforandroid.home.HomeFragment
import com.zeepy.zeepyforandroid.lookaround.LookAroundFragment
import com.zeepy.zeepyforandroid.myprofile.MyProfileFrameFragment
import java.lang.RuntimeException

class MainFrameAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> LookAroundFragment()
            2 -> CommunityFrameFragment()
            3 -> MyProfileFrameFragment()
            else -> throw RuntimeException("Error Fragment load")
        }
    }
}