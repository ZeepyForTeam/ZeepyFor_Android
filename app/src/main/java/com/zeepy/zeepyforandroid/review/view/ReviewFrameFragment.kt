package com.zeepy.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentReviewFrameBinding
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFrameFragment : BaseFragment<FragmentReviewFrameBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>()
    private lateinit var navController: NavController

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
//        changeToolbar()
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

//    private fun changeToolbar() {
//        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(),R.font.nanum_square_round_extrabold),Typeface.NORMAL)
//
//        viewModel.currentFragment.observe(viewLifecycleOwner){ reviewNotice ->
//            binding.tvReviewNotice.apply {
//                text = getString(reviewNotice.text)
//                visibility = View.VISIBLE
//                reviewNotice.map.forEach{
//                    text.toSpannable().setSpan(CustomTypefaceSpan(typeface),it.key,it.value,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//                }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.viewModelStore?.clear()
    }
}