package com.example.zeepyforandroid.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunitySearchAddressBinding

class CommunitySearchAddressFragment : BaseFragment<FragmentCommunitySearchAddressBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunitySearchAddressBinding {
        return FragmentCommunitySearchAddressBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewTheme() {
        binding.toolbar.apply {
            setTitle("")
        }
    }

}