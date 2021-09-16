package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMyprofileFrameBinding

class MyProfileFrameFragment : BaseFragment<FragmentMyprofileFrameBinding>() {
    private lateinit var navController: NavController

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyprofileFrameBinding {
        return FragmentMyprofileFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.myprofile_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setToolbar()
        unsetBottomNavigationBar()
        swipeOnlyOnMain()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setTitle("마이페이지")

            // NOTE: navController.previousBackStackEntry is null at startDestination
            navController.addOnDestinationChangedListener { _, destination, _ ->
                //Log.e("WHAT IS DEST NOW?", destination.toString())
                //Log.e("navcontroller.currentBackStackEntry", "" + navController.currentBackStackEntry)
                //Log.e("navcontroller.previousBackStackEntry", "" + navController.previousBackStackEntry)

                if (navController.previousBackStackEntry != null) {
                    setBackButton {
                        navController.popBackStack()
                    }
                } else {
                    clearButton()
                    setTitle("마이페이지")
                    // TODO: Below visibility setting can be deleted (Already done in ManageReviewFragment)
                    if (visibility == View.GONE) {
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun unsetBottomNavigationBar() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navController.addOnDestinationChangedListener { navController, destination, _ ->
            if (destination.id != R.id.myProfileFragment && navController.previousBackStackEntry != null) {
                navBar?.visibility = View.GONE
            } else {
                navBar?.visibility = View.VISIBLE
            }
        }
    }

    private fun swipeOnlyOnMain() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager_main)

        navController.addOnDestinationChangedListener { _, _, _ ->
            viewPager?.isUserInputEnabled = navController.previousBackStackEntry == null
        }
    }
}