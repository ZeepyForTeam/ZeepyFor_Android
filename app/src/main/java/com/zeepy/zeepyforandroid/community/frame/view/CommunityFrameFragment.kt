package com.zeepy.zeepyforandroid.community.frame.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityFrameBinding
import com.zeepy.zeepyforandroid.home.DirectTransitionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFrameFragment: BaseFragment<FragmentCommunityFrameBinding>() {
    private lateinit var navController: NavController

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityFrameBinding {
        return FragmentCommunityFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_community) as NavHostFragment
        navController = navHostFragment.navController
    }
}