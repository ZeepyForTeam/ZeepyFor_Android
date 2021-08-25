package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityFrameBinding
import com.zeepy.zeepyforandroid.databinding.FragmentMyprofileFrameBinding

class MyProfileFrameFragment: BaseFragment<FragmentMyprofileFrameBinding>() {
    private lateinit var navController: NavController

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyprofileFrameBinding {
        return FragmentMyprofileFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.myprofile_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar.run {
            setTitle("마이페이지")

            setBackButton{
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                } else {
                    Navigation.findNavController(binding.root).popBackStack()
                }
                if (navController.currentDestination?.id == R.id.myProfileFragment) {
                    setTitle("마이페이지")
                }
            }
        }
    }
}