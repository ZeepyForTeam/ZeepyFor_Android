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
import com.zeepy.zeepyforandroid.databinding.FragmentWishlistBinding

class WishListFragment: BaseFragment<FragmentWishlistBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWishlistBinding {
        return FragmentWishlistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressed()
        setToolbar()
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("찜 목록")
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}