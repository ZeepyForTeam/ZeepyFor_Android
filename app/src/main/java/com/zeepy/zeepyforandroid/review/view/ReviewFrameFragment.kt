package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentReviewFrameBinding
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFrameFragment : BaseFragment<FragmentReviewFrameBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>()
    private lateinit var navController: NavController
    private val args: ReviewFrameFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewFrameBinding {
        return FragmentReviewFrameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val navHostFragment = childFragmentManager.findFragmentById(R.id.review_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setToolbar()
        checkIsAddressRegistered()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                } else {
                    Navigation.findNavController(binding.root).popBackStack()
                }
            }
        }
    }

    private fun checkIsAddressRegistered() {
        if (args.isAddressRegisterd) {
            navController.navigate(R.id.action_selectAddressFragment_to_searchAddressFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.selectAddressFragment, true)
                    .build()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.viewModelStore?.clear()
    }
}