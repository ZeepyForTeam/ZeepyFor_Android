package com.example.zeepyforandroid.community.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.RuntimeException

class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        initViewPager()
    }

    private fun setToolbar() {
        binding.toolbar.setTitle("을지로 3가")
        binding.toolbar.setCommunityLocation()
    }

    private fun initViewPager() {
        binding.viewpagerCommunity.adapter = CommunityFrameAdapter(this@CommunityFragment)
        TabLayoutMediator(binding.tablayout, binding.viewpagerCommunity) {tab, pos ->
            tab.text = when(pos) {
                0 -> TAB_NAME[0]
                1 -> TAB_NAME[1]
                else -> throw RuntimeException("TabLayout Error")
            }
        }.attach()
    }

    companion object {
        private val TAB_NAME = listOf("이야기ZIP", "참여목록")
    }
}