package com.example.zeepyforandroid.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWritePostingBinding
import com.example.zeepyforandroid.util.ItemDecoration

class WritePostingFragment: BaseFragment<FragmentWritePostingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWritePostingBinding {
        return FragmentWritePostingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setCollectList()
        setNextButton()
    }

    private fun setToolbar() {
        binding.toolbarWritePosting.run {
            setTitle("글 작성하기")
            setBackButton{

            }
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setCommunityUsableButton()
        }
    }

    private fun setCollectList() {
        binding.recyclerviewCollectList.run {
            adapter = CollectListAdapter()
            isNestedScrollingEnabled = false
            addItemDecoration(ItemDecoration(16,0))
        }
    }
}