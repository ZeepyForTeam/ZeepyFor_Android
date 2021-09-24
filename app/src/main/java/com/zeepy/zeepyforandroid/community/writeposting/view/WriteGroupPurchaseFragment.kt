package com.zeepy.zeepyforandroid.community.writeposting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.CollectContentModel
import com.zeepy.zeepyforandroid.community.writeposting.viewmodel.WriteGroupPurchaseViewModel
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentWriteGroupPurchaseBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteGroupPurchaseFragment: BaseFragment<FragmentWriteGroupPurchaseBinding>() {
    private val viewModel: WriteGroupPurchaseViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteGroupPurchaseBinding {
        return FragmentWriteGroupPurchaseBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setCollectList()
        setNextButton()
        activateNextButton()
        completeUpload()
    }

    private fun setToolbar() {
        binding.toolbarWritePosting.run {
            setTitle("글 작성하기")
            setBackButton{
                findNavController().popBackStack()
            }
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
            onClick {
                showPostingRegisterDialog()
            }
        }
    }

    private fun completeUpload() {
        viewModel.successUpload.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }
    }

    private fun setCollectList() {
        binding.recyclerviewCollectList.run {
            adapter = CollectListAdapter (object: CollectListAdapter.CollectListInterface {
                override fun select(item: CollectContentModel) {
                    viewModel.addCollectList(item)
                    viewModel.makeInstructionString()
                }
                override fun unSelect(item: CollectContentModel) {
                    viewModel.removeCollectList(item)
                }
            })
            isNestedScrollingEnabled = false
            addItemDecoration(ItemDecoration(16,0))
        }
    }

    private fun activateNextButton() {
        val essentialLiveDataList = listOf(viewModel.title, viewModel.productName, viewModel.productPrice, viewModel.purchaseSite, viewModel.sharingMethod, viewModel.content, viewModel.targetCount)
        essentialLiveDataList.forEach {
            it.observe(viewLifecycleOwner) {
                viewModel.checkEveryInfoEntered()
            }
        }
        viewModel.isEveryInfoEntered.observe(viewLifecycleOwner) { isEveryEntered ->
            if (isEveryEntered) {
                binding.btnNext.setCommunityUsableButton()
            } else {
                binding.btnNext.setUnUsableButton()
            }
        }
    }

    private fun showPostingRegisterDialog() {
        val registerReviewDialog = ZeepyDialogBuilder("글을 등록하시겠습니까?", "community")
            .setContent("*공동구매 글의 경우 참여자가 1명 이상일\n경우 글을 삭제하거나 수정하실 수 없습니다.\n\n*허위/중복/성의없는 정보 또는 비방글을\n작성할 경우, 서비스 이용이 제한될 수 있습니다.")
            .setLeftButton(R.drawable.box_grayf9_8dp,"취소")
            .setRightButton(R.drawable.box_green33_8dp,"확인")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                    viewModel.uploadPostingToZeepyServer()
                }
            }).build()
        registerReviewDialog.show(childFragmentManager, this.tag)
    }
}