package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSelectAddressBinding
import com.example.zeepyforandroid.review.view.adapter.AddressAdapter
import com.example.zeepyforandroid.review.data.dto.AddressModel
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice


class SelectAddressFragment : BaseFragment<FragmentSelectAddressBinding>() {
    private val viewModel: WriteReviewViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectAddressBinding {
        return FragmentSelectAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.changeCurrentFragment(ReviewNotice.SELECT_ADDRESS)
        initView()
        setDatas()
        goToSearchAddress()

    }

    private fun initView() {
        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
            onClick {
                goToWriteDetailAddress()
            }
        }

        binding.rvAddressList.run {
            adapter = AddressAdapter(object : AddressAdapter.ClickListener{
                override fun delete(item: AddressModel) {
                    viewModel.deleteAddress(item)
                }
                override fun select(item: AddressModel) {
                    viewModel.changeAddressSelected(item.address)
                    binding.btnNext.usableButton()
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
        Navigation.findNavController(binding.root).navigate(R.id.action_selectAddressFragment_to_lessorPersonalityFragment)
    }

    private fun goToSearchAddress() {
        binding.tvRegisterAddress.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_selectAddressFragment_to_searchAddressFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.addressSearchQuery.value = null
    }
}