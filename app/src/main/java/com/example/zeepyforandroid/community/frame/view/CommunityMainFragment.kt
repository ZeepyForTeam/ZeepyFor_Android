package com.example.zeepyforandroid.community.frame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.example.zeepyforandroid.databinding.FragmentCommunityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityMainFragment : BaseFragment<FragmentCommunityMainBinding>() {
    private val viewModel by activityViewModels<CommunityFrameViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityMainBinding {
        return FragmentCommunityMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        initViewPager()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("을지로 3가")
            setCommunityLocation()
            setRightButton(R.drawable.ic_btn_write) {
                findNavController().navigate(R.id.action_communityMainFragment_to_communitySelectCategoryFragment)
            }
            setRightButtonMargin(32)

            setOnClickListener {
                requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_communitySearchAddressFragment)
            }
        }
    }

    private fun initViewPager() {
        binding.viewpagerCommunity.adapter = CommunityFrameAdapter(this@CommunityMainFragment)
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