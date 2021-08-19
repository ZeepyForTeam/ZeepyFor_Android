package com.zeepy.zeepyforandroid.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentChangeAddressBinding

class ChangeAddressFragment: BaseFragment<FragmentChangeAddressBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangeAddressBinding {
        return FragmentChangeAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToChangeAddress()
        exit()
    }

    private fun goToChangeAddress() {
        binding.textviewChangeAddress.setOnClickListener {
            val action = ChangeAddressFragmentDirections.actionChangeAddressFragmentToReviewFrameFragment()
            action.isJustRegisterAddress = true
            findNavController().navigate(action)
        }
    }

    private fun exit() {
        binding.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}