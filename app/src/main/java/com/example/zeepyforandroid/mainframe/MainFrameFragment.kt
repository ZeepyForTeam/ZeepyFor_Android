package com.example.zeepyforandroid.mainframe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.FragmentMainFrameBinding
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class MainFrameFragment : BaseFragment<FragmentMainFrameBinding>() {
    private val viewModel: MainFrameViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainFrameBinding {
        return FragmentMainFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        configureBottomNavigation()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.pageIdx.observe(viewLifecycleOwner) { pageIdx ->
            binding.viewpagerMain.currentItem = pageIdx
            selectBottomNavigationMenu(pageIdx)
        }
    }

    private fun initViewPager() = binding.viewpagerMain.run {
        offscreenPageLimit = 3
        adapter = MainFrameAdapter(this@MainFrameFragment)
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.changePageIdx(position)
            }
        })
    }

    private fun configureBottomNavigation() = binding.bottomNavigation.run {
        itemIconTintList = null

        setOnItemSelectedListener { item ->
            Log.e("pageIdx", viewModel.pageIdx.value.toString())
            viewModel.changePageIdx(
                when (item.itemId) {
                    R.id.homeFragment -> 0
                    R.id.lookAroundFragment -> 1
                    R.id.communityFrameFragment -> 2
                    R.id.myProfileFragment -> 3
                    else -> throw RuntimeException("error error")
                }
            )
            true
        }
    }

    private fun selectBottomNavigationMenu(pageIdx: Int) {
        binding.bottomNavigation.selectedItemId = when (pageIdx) {
            0 -> R.id.homeFragment
            1 -> R.id.lookAroundFragment
            2 -> R.id.communityFrameFragment
            3 -> R.id.myProfileFragment
            else -> throw RuntimeException("Bottom Navigation Item Error")
        }
    }
}