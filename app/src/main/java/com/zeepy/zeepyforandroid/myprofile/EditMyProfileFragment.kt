package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentEditMyProfileBinding

class EditMyProfileFragment : BaseFragment<FragmentEditMyProfileBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditMyProfileBinding {
        return FragmentEditMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("내 정보 수정")
    }

    private fun setOnClickListeners() {
        binding.btnSubmitChange.setOnClickListener {
            changePasswordShowConfirmDialog(this)
        }

        binding.tvLogout

        binding.tvWithdraw.setOnClickListener {
            //findNavController().navigate()
        }
    }
}