package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        setDatas()
        goToSearchAddress()
        getBuildingId()
    }

    private fun initView() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
            onClick {
                goToWriteDetailAddress()
            }
        }

        binding.recyclerviewAddressList.run {
            adapter = AddressAdapter(requireContext(), object : AddressAdapter.ClickListener{
                override fun delete(item: LocalAddressEntity) {
                    showDeleteAddressDialog(item)
                }
                override fun select(item: LocalAddressEntity) {
                    viewModel.changeAddressSelected(item)
                    binding.btnNext.setUsableButton()
                }
            })
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun showDeleteAddressDialog(address: LocalAddressEntity) {
        val deleteAddressDialog = ZeepyDialogBuilder("정말 삭제하시겠습니까?", false)
            .setLeftButton(R.drawable.box_grayf9_8dp,"삭제")
            .setRightButton(R.drawable.box_blue_59_8dp, "취소")
            .setDialogClickListener(object : DialogClickListener{
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    viewModel.deleteAddress(address)
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }
            })
            .build()

        deleteAddressDialog.show(childFragmentManager, this.tag)
    }

    private fun setDatas() {
        viewModel.addressListRegistered.observe(viewLifecycleOwner){
            (binding.recyclerviewAddressList.adapter as AddressAdapter).submitList(it)
        }
    }

    private fun getBuildingId() {
        viewModel.addressSelected.observe(viewLifecycleOwner) {
            viewModel.getBuildingId()
        }
    }

    private fun goToWriteDetailAddress() {
        viewModel.addressListRegistered.value?.let { addresses ->
            val action = SelectAddressFragmentDirections.actionSelectAddressFragmentToWriteDetailAddressFragment(viewModel.addressSelected.value!!)
            findNavController().navigate(action)
        }
    }

    private fun goToSearchAddress() {
        binding.tvRegisterAddress.setOnClickListener {
            viewModel.addressListRegistered.value?.let { addresses ->
                if (addresses.count() >= 3) {
                    ZeepyDialogBuilder("최대 3개의 주소까지\n등록이 가능해요!",false)
                        .setSingleButton(true)
                        .setDialogClickListener(object : DialogClickListener{
                            override fun clickLeftButton(dialog: ZeepyDialog) {
                                dialog.dismiss()
                            }
                            override fun clickRightButton(dialog: ZeepyDialog) {}
                        })
                        .build().show(childFragmentManager,this.tag)
                } else {
                    findNavController().navigate(R.id.action_selectAddressFragment_to_searchAddressFragment)
                }
            }
        }
        binding.textviewRegisterAddressNoAddress.setOnClickListener {
            findNavController().navigate(R.id.action_selectAddressFragment_to_searchAddressFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.changeAddressSearchgQuery("")
    }
}