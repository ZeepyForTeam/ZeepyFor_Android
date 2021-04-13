package com.example.zeepyforandroid.mainframe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.FragmentMainFrameBinding
import java.lang.RuntimeException


class MainFrameFragment : Fragment() {
    private lateinit var binding: FragmentMainFrameBinding
    private val viewModel: MainFrameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainFrameBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
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
        setOnNavigationItemSelectedListener {
            Log.e("pageIdx", viewModel.pageIdx.value.toString())
            viewModel.changePageIdx(
                when (it.itemId) {
                    R.id.homeFragment -> 0
                    R.id.lookAroundFragment -> 1
                    R.id.communityFragment -> 2
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
            2 -> R.id.communityFragment
            3 -> R.id.myProfileFragment
            else -> throw RuntimeException("Bottom Navigation Item Error")
        }
    }
}