package com.example.zeepyforandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        writeReview()

    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("을지로 3가")
            setCommunityLocation()
            setRightButton(R.drawable.ic_btn_write) {
                findNavController().navigate(R.id.action_communityMainFragment_to_communitySelectCategoryFragment)
            }
            setRightButtonMargin(32)

            setOnClickListener {
                requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_communitySearchAddressFragment)
            }
        }
    }

    private fun writeReview() {
        binding.buttonWriteReview.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFrameFragment_to_reviewFrameFragment)
        }
    }
}