package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMyProfileBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyProfileOptionsAdapter


class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding {
        return FragmentMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsOnClickListener()
        setOptionsRecyclerView()
    }

    private fun setButtonsOnClickListener() {
        binding.ivManageAddress.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageAddressFragment)
        }
        binding.ivManageReview.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageReviewFragment)
        }
        binding.ivWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_wishListFragment)
        }
    }

    private fun setOptionsRecyclerView() {
        val options = arrayOf("환경설정", "문의 및 의견 보내기", "신고하기", "지피의 지기들", "현재 버전 1.1")

        binding.rvOptionsList.apply {
            setHasFixedSize(true)
            adapter = MyProfileOptionsAdapter(options)
        }
    }
}