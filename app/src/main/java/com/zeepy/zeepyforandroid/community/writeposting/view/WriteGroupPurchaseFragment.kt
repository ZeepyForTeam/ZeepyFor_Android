package com.zeepy.zeepyforandroid.community.writeposting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.CollectContentModel
import com.zeepy.zeepyforandroid.community.writeposting.viewmodel.WriteGroupPurchaseViewModel
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
        uploadPosting()
        activateNextButton()
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

    private fun uploadPosting() {
        binding.btnNext.setOnClickListener {
            viewModel.sendRequestData()
            val argsToSend = viewModel.requestWritePosting.value
            val action = WriteGroupPurchaseFragmentDirections.actionWriteGroupPurchaseFragmentToCommunityLoadPictureFragment(argsToSend)
            findNavController().navigate(action)
        }
    }
}