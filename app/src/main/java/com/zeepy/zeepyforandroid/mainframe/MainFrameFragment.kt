package com.zeepy.zeepyforandroid.mainframe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.FragmentMainFrameBinding
import com.zeepy.zeepyforandroid.home.DirectTransitionListener
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException
import javax.inject.Inject

@AndroidEntryPoint
class MainFrameFragment : BaseFragment<FragmentMainFrameBinding>(), DirectTransitionListener {
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
        setDestListener()
    }

    private fun setDestListener() {
        // 로그인 후 홈뷰로 이동
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mainFrameFragment
                && childFragmentManager.findFragmentById(R.id.myprofile_nav_host_fragment)
                    ?.findNavController()?.previousBackStackEntry == null
                && viewModel.pageIdx.value == 3
            ) {
                viewModel.changePageIdx(0)
            }
        }
    }

    override fun applyCommunityFilter(type: String) {
        (requireActivity() as MainActivity).initialCommunityType = type

        viewModel.changePageIdx(2)
        initViewPager()
    }

    override fun applyLookAroundFilter(type: String) {
        (requireActivity() as MainActivity).initialLookAroundType = type

        viewModel.changePageIdx(1)
        initViewPager()
    }

    override fun comeBackHome() {
        viewModel.changePageIdx(0)
        initViewPager()
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
                    R.id.myProfileFrameFragment -> 3
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
            3 -> R.id.myProfileFrameFragment
            else -> throw RuntimeException("Bottom Navigation Item Error")
        }
    }
}