package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentReviewFrameBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.setText
import com.example.zeepyforandroid.util.CustomTypefaceSpan

class ReviewFrameFragment : BaseFragment<FragmentReviewFrameBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()
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
        changeToolbar()
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

    private fun changeToolbar() {
        viewModel.currentFragment.observe(viewLifecycleOwner){ reviewUi ->
            binding.tvReviewNotice.text = getString(reviewUi.text)
            binding.tvReviewNotice.visibility = View.VISIBLE
            val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(),R.font.nanum_square_round_extrabold),Typeface.NORMAL)

            for(i in reviewUi.map){
                binding.tvReviewNotice.text.toSpannable().setSpan(CustomTypefaceSpan(typeface), i.key, i.value, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }

}