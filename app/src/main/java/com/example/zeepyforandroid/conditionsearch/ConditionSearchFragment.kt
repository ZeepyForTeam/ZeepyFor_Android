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
    private var roomOptionCnt = 0;
    private var payOptionCnt = 0;

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
        binding.cbOptionOneroom.setOnCheckedChangeListener(CheckboxListener())
        binding.cbOptionTworoom.setOnCheckedChangeListener(CheckboxListener())

        setOptionChoice()
        setNextButton()
        removeSliderPadding()


    }

    override fun onResume() {
        super.onResume()
    }

    inner class CheckboxListener: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when (buttonView) {
                binding.cbOptionWalsae ->
                    if (isChecked) {
                        payOptionCnt++
                        binding.tvPrice.visibility = View.VISIBLE
                        binding.rsMonthlypay.visibility = View.VISIBLE
                        binding.tvMonthlypay.visibility = View.VISIBLE
                        binding.tvMonthlypayRange.visibility = View.VISIBLE
                        if (!binding.cbOptionJeonsae.isChecked) {
                            binding.rsDeposit.visibility = View.VISIBLE
                            binding.tvDeposit.visibility = View.VISIBLE
                            binding.tvDepositRange.visibility = View.VISIBLE
                        }
                    }
                    else {
                        payOptionCnt--
                        binding.rsMonthlypay.visibility = View.GONE
                        binding.tvMonthlypay.visibility = View.GONE
                        binding.tvMonthlypayRange.visibility = View.GONE
                        if (!binding.cbOptionJeonsae.isChecked) {
                            binding.tvPrice.visibility = View.GONE
                            binding.rsDeposit.visibility = View.GONE
                            binding.tvDeposit.visibility = View.GONE
                            binding.tvDepositRange.visibility = View.GONE
                        }
                    }
                binding.cbOptionJeonsae ->
                    if (isChecked) {
                        payOptionCnt++
                        binding.tvPrice.visibility = View.VISIBLE
                        if (!binding.cbOptionWalsae.isChecked) {
                            binding.rsDeposit.visibility = View.VISIBLE
                            binding.tvDeposit.visibility = View.VISIBLE
                            binding.tvDepositRange.visibility = View.VISIBLE
                        }
                    }
                    else {
                        payOptionCnt--
                        if (!binding.cbOptionWalsae.isChecked) {
                            binding.tvPrice.visibility = View.GONE
                            binding.rsDeposit.visibility = View.GONE
                            binding.tvDeposit.visibility = View.GONE
                            binding.tvDepositRange.visibility = View.GONE
                        }
                    }
                binding.cbOptionOneroom ->
                    if (isChecked) roomOptionCnt++
                    else roomOptionCnt--
                binding.cbOptionTworoom ->
                    if (isChecked) roomOptionCnt++
                    else roomOptionCnt--
            }
            if (roomOptionCnt >= 1 && payOptionCnt >= 1)
                binding.btnNext.usableButton()
            else
                binding.btnNext.unUseableButton()
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

    private fun removeSliderPadding() {
        binding.rsDeposit.setPadding(0, 0, 0, 0)
        binding.rsMonthlypay.setPadding(0, 0, 0, 0)
    }

}