package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentManageAddressBinding
import com.zeepy.zeepyforandroid.review.data.entity.AddressModel
import com.zeepy.zeepyforandroid.review.view.adapter.AddressAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration
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
                override fun delete(item: LocalAddressEntity) {
                    viewModel.deleteAddress(item)
                }
                override fun select(item: LocalAddressEntity) {
                    viewModel.changeAddressSelected(item)
                }
            })
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun setDatas() {
        viewModel.addressListRegistered.observe(viewLifecycleOwner){
            (binding.rvAddressList.adapter as AddressAdapter).submitList(it)
        }
    }
}