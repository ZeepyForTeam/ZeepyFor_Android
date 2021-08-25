package com.zeepy.zeepyforandroid.myprofile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentManageAddressBinding
import com.zeepy.zeepyforandroid.review.data.entity.AddressModel
import com.zeepy.zeepyforandroid.review.view.SelectAddressFragmentDirections
import com.zeepy.zeepyforandroid.review.view.adapter.AddressAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

class ManageAddressFragment : BaseFragment<FragmentManageAddressBinding>() {
    // 리뷰 뷰모델 인스턴스
    private val sharedViewModel by activityViewModels<WriteReviewViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageAddressBinding {
        return FragmentManageAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        initView()
        setDatas()
        goToSearchAddress()
        getBuildingId()
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("주소 관리")
    }

    private fun initView() {
        binding.recyclerviewAddressList.run {
            adapter = AddressAdapter(requireContext(), object : AddressAdapter.ClickListener{
                override fun delete(item: LocalAddressEntity) {
                    showDeleteAddressDialog(item)
                }
                override fun select(item: LocalAddressEntity) {
                    sharedViewModel.changeAddressSelected(item)
                }
            })
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun showDeleteAddressDialog(address: LocalAddressEntity) {
        val deleteAddressDialog = ZeepyDialogBuilder("정말 삭제하시겠습니까?", false)
            .setLeftButton(R.drawable.box_grayf9_8dp,"삭제")
            .setRightButton(R.drawable.box_blue_59_8dp, "취소")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    sharedViewModel.deleteAddress(address)
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
        sharedViewModel.addressListRegistered.observe(viewLifecycleOwner){
            (binding.recyclerviewAddressList.adapter as AddressAdapter).submitList(it)
        }
    }

    private fun getBuildingId() {
        sharedViewModel.addressSelected.observe(viewLifecycleOwner) {
            sharedViewModel.getBuildingId()
        }
    }

    private fun goToSearchAddress() {
        val uri = Uri.parse("myapp://search")
        binding.tvRegisterAddress.setOnClickListener {
            sharedViewModel.addressListRegistered.value?.let { addresses ->
                if (addresses.count() >= 3) {
                    ZeepyDialogBuilder("최대 3개의 주소까지\n등록이 가능해요!",false)
                        .setSingleButton(true)
                        .setDialogClickListener(object : DialogClickListener {
                            override fun clickLeftButton(dialog: ZeepyDialog) {
                                dialog.dismiss()
                            }
                            override fun clickRightButton(dialog: ZeepyDialog) {}
                        })
                        .build().show(childFragmentManager,this.tag)
                } else {
                    findNavController().navigate(uri)
                }
            }
        }
        binding.textviewRegisterAddressNoAddress.setOnClickListener {
            findNavController().navigate(uri)
        }
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.changeAddressSearchgQuery("")
    }
}