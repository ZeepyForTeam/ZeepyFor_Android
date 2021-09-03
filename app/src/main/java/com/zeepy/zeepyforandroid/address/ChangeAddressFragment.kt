package com.zeepy.zeepyforandroid.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentChangeAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeAddressFragment : BaseFragment<FragmentChangeAddressBinding>() {
    private val viewModel by viewModels<ChangeAddressViewModel>()
    private val args: ChangeAddressFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangeAddressBinding {
        return FragmentChangeAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeAddressList(args.addressList.toMutableList())

        goToChangeAddress()
        attachAddressRecyclerView()
        loadUnSelectedAddress()
        exit()
    }

    private fun attachAddressRecyclerView() {
        binding.recyclerviewAddress.run {
            adapter = ChangeAddressAdapter { selectedAddress ->
                viewModel.changeSelectedAddress(selectedAddress)
                updateSeletedAddress()
                findNavController().popBackStack()
            }
        }
    }

    private fun updateSeletedAddress() {
        viewModel.addressList.observe(viewLifecycleOwner) {
            viewModel.updateSelectedAddressToServer()
        }
    }

    private fun loadUnSelectedAddress() {
        val unSelectedAddress = viewModel.addressList.value?.filter { !it.isAddressCheck }
        (binding.recyclerviewAddress.adapter as ChangeAddressAdapter).submitList(unSelectedAddress)
    }

    private fun goToChangeAddress() {
        binding.textviewChangeAddress.setOnClickListener {
            val action =
                ChangeAddressFragmentDirections.actionChangeAddressFragmentToReviewFrameFragment()
            action.isJustRegisterAddress = true
            action.isCommunityTheme = args.isCommunityTheme
            findNavController().navigate(action)
        }
    }

    private fun exit() {
        binding.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}