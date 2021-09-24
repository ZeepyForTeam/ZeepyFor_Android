package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentZiggysBinding

class ZiggysFragment : BaseFragment<FragmentZiggysBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentZiggysBinding {
        return FragmentZiggysBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressed()
        setToolbar()
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("지피의 지기들")
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}