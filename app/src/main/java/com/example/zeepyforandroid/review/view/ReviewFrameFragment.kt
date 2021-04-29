package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentReviewFrameBinding

class ReviewFrameFragment : BaseFragment<FragmentReviewFrameBinding>() {
    private lateinit var navController: NavController
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewFrameBinding {
        return FragmentReviewFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.review_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                    Log.e("back", navController.previousBackStackEntry.toString())
                } else {
                    Navigation.findNavController(binding.root).popBackStack()
                }
            }
        }
    }
}