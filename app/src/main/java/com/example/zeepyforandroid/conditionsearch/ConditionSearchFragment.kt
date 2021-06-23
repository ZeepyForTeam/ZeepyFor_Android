package com.example.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.example.zeepyforandroid.conditionsearch.viewmodel.ConditionSearchViewModel
import com.example.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.example.zeepyforandroid.util.ItemDecoration


class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){

    private val viewModel: ConditionSearchViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchByConditionBinding {
        return FragmentSearchByConditionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.cbOptionWalsae.setOnCheckedChangeListener(CheckboxListener())
        binding.cbOptionJeonsae.setOnCheckedChangeListener(CheckboxListener())

        setOptionChoice()
        setNextButton()
        removeRangerPadding()


    }

    override fun onResume() {
        super.onResume()
    }

    inner class CheckboxListener: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when (buttonView) {
                binding.cbOptionWalsae ->
                    if (isChecked) {
                        binding.rsDeposit.visibility = View.VISIBLE
                        binding.rsMonthlypay.visibility = View.VISIBLE
                    }
                    else if (binding.cbOptionJeonsae.isChecked) {
                        binding.rsMonthlypay.visibility = View.GONE
                    }
                    else {
                        binding.rsDeposit.visibility = View.GONE
                        binding.rsMonthlypay.visibility = View.GONE
                    }
                binding.cbOptionJeonsae ->
                    if (isChecked) binding.rsDeposit.visibility = View.VISIBLE
            }
        }
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
            unUseableButton()
        }
    }

    private fun removeRangerPadding() {
        binding.rsDeposit.setPadding(0, 0, 0, 0)
        binding.rsMonthlypay.setPadding(0, 0, 0, 0)
    }



    // TODO 언제 다음으로 버튼을 보이게 할 건지 조건을 줘야 함
    // 적어도 하나의 건물 유형이 선택되고 적어도 하나의 거래 종류가 선택되어야 함
    private fun enableNextButton(map:Map<Int,Int>) {
        if(map.size == 4) {
            binding.btnNext.usableButton()
            //goToWriteHouseInfo()
        } else {
            binding.btnNext.unUseableButton()
        }
    }

}