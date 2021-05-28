package com.example.zeepyforandroid.community.frame.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zeepyforandroid.community.participationlist.ParticipationListFragment
import com.example.zeepyforandroid.community.storyzip.StoryZipFragment
import java.lang.RuntimeException

class CommunityFrameAdapter(fragment:Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> StoryZipFragment()
            1 -> ParticipationListFragment()
            else -> throw RuntimeException("Community Fragment Error")
        }
    }

}