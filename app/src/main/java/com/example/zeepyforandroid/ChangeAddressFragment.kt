package com.example.zeepyforandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentChangeAddressBinding

class ChangeAddressFragment: BaseFragment<FragmentChangeAddressBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangeAddressBinding {
        return FragmentChangeAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exit()
    }

    private fun exit() {
        binding.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}