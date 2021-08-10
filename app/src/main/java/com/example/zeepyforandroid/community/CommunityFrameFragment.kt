package com.example.zeepyforandroid.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunityFrameBinding
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