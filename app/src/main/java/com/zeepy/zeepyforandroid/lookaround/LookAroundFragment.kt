package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookAroundBinding


class LookAroundFragment : BaseFragment<FragmentLookAroundBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookAroundBinding {
        return FragmentLookAroundBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMap.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFrameFragment_to_mapFragment)
        }

        binding.btnConditionsearch.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFrameFragment_to_ConditionSearchFragment)
        }
    }
}