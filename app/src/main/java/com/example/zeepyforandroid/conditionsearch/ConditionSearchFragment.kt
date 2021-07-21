package com.example.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.example.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.example.zeepyforandroid.util.ItemDecoration


class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchByConditionBinding {
        return FragmentSearchByConditionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOptionChoice()
        setNextButton()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setOptionChoice() {
        binding.rvFurnitureOption.run {
            adapter = ConditionOptionAdapter()
            addItemDecoration(ItemDecoration(8, 8))
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
        }
    }


    // TODO 언제 다음으로 버튼을 보이게 할 건지 조건을 줘야 함
    private fun enableNextButton(map:Map<Int,Int>) {
        if(map.size == 4) {
            binding.btnNext.setUsableButton()
            //goToWriteHouseInfo()
        } else {
            binding.btnNext.setUnUsableButton()
        }
    }

}