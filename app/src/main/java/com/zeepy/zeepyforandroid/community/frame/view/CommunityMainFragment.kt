package com.zeepy.zeepyforandroid.community.frame.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityMainFragment : BaseFragment<FragmentCommunityMainBinding>() {
    private val viewModel by viewModels<CommunityFrameViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityMainBinding {
        return FragmentCommunityMainBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressListFromLocal()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        initViewPager()
        changeZipFragment()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            viewModel.selectedAddress.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    setTitle("주소 등록하기")
                } else {
                    setTitle(it)
                }
            }
            setCommunityLocation()
            setRightButton(R.drawable.ic_btn_write) {
                findNavController().navigate(R.id.action_communityMainFragment_to_communitySelectCategoryFragment)
            }
            setRightButtonMargin(32)

            binding.textviewToolbar.setOnClickListener {
                requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_changeAddressFragment)
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

    private fun changeZipFragment() {
        binding.viewpagerCommunity.run {
            viewModel.changeCurrentFragmentId(this.currentItem)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.changeCurrentFragmentId(position)
                }
            })
        }
    }

    companion object {
        private val TAB_NAME = listOf("이야기ZIP", "나의ZIP")
    }
}