package com.example.zeepyforandroid.mainframe

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zeepyforandroid.community.frame.view.CommunityFragment
import com.example.zeepyforandroid.home.HomeFragment
import com.example.zeepyforandroid.lookaround.LookAroundFragment
import com.example.zeepyforandroid.myprofile.MyProfileFragment
import java.lang.RuntimeException

class MainFrameAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> LookAroundFragment()
            2 -> CommunityFragment()
            3 -> MyProfileFragment()
            else -> throw RuntimeException("Error Fragment load")
        }
    }
}