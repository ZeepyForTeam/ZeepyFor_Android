package com.example.zeepyforandroid.community.frame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunityBinding
import com.example.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {
    private val viewModel by viewModels<CommunityFrameViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostingList()

        setToolbar()
        initViewPager()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("을지로 3가")
            setCommunityLocation()
            setRightButton(R.drawable.ic_btn_write) {
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToPostingDetailFragment(
                    viewModel.postingList.value?.get(0)!!
                )
                findNavController().navigate(action)
            }
            setRightButtonMargin(32)
        }
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