package com.zeepy.zeepyforandroid.myprofile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentManageReviewBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyReviewAdapter
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageReviewFragment: BaseFragment<FragmentManageReviewBinding>() {
    private val viewModel by viewModels<MyProfileViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageReviewBinding {
        return FragmentManageReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setOnBackPressed()
        setToolbar()
        initRecyclerView()
        setUserReviews()
    }

    private fun setUserReviews() {
        viewModel.userReviews.observe(viewLifecycleOwner) {
            Log.e("userReviews", it.toString())
            (binding.rvReviewList.adapter as MyReviewAdapter).submitList(it.simpleReviewDtoList)
        }
    }

    private fun initRecyclerView() {
        val uri = Uri.parse("myapp://detailedReview")
        binding.rvReviewList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = MyReviewAdapter {
                findNavController().navigate(uri)
                requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
                    ?.visibility = View.GONE
            }
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.apply {
                setTitle("리뷰 관리")
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                }
            }
    }

    /**
     * onBackPressed CallBack Function
     * TODO: Modify the Navigation Architecture so that we won't need this
     */
    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}