package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentSelectAddressBinding
import com.zeepy.zeepyforandroid.review.view.adapter.AddressAdapter
import com.zeepy.zeepyforandroid.review.data.entity.AddressModel
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.ReviewNotice

class SelectAddressFragment : BaseFragment<FragmentSelectAddressBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectAddressBinding {
        return FragmentSelectAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setDatas()
        goToSearchAddress()
    }

    private fun initView() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
            onClick {
                goToWriteDetailAddress()
            }
        }

        binding.rvAddressList.run {
            adapter = AddressAdapter(requireContext(), object : AddressAdapter.ClickListener{
                override fun delete(item: AddressModel) {
                    viewModel.deleteAddress(item)
                }
                override fun select(item: AddressModel) {
                    viewModel.changeAddressSelected(item.address)
                    binding.btnNext.setUsableButton()
                }
            })
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun setDatas() {
        viewModel.addressList.observe(viewLifecycleOwner){
            (binding.rvAddressList.adapter as AddressAdapter).submitList(viewModel.addressList.value)
        }
    }

    private fun goToWriteDetailAddress() {
        Navigation.findNavController(binding.root).navigate(R.id.action_selectAddressFragment_to_writeDetailAddressFragment)
    }

    private fun goToSearchAddress() {
        binding.tvRegisterAddress.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_selectAddressFragment_to_searchAddressFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.changeAddressSearchgQuery("")
    }
}