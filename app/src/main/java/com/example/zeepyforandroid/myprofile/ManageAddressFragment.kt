package com.example.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentManageAddressBinding
import com.example.zeepyforandroid.databinding.FragmentWithdrawalBinding
import com.example.zeepyforandroid.review.data.entity.AddressModel
import com.example.zeepyforandroid.review.view.adapter.AddressAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageAddressFragment : BaseFragment<FragmentManageAddressBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageAddressBinding {
        return FragmentManageAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        binding.rvAddressList.run {
            adapter = AddressAdapter(requireContext(), object : AddressAdapter.ClickListener{
                override fun delete(item: AddressModel) {
                    viewModel.deleteAddress(item)
                }
                override fun select(item: AddressModel) {
                    viewModel.changeAddressSelected(item.address)
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
}