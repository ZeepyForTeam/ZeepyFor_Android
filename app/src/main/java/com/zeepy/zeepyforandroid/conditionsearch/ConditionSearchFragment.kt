package com.zeepy.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.google.android.material.slider.RangeSlider


class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){

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
        binding.rsMonthlypay.addOnChangeListener(valueChangeListener)
        binding.rsDeposit.addOnChangeListener(valueChangeListener)
        setOptionChoice()
        setNextButton()
        removeSliderPadding()
    }

    override fun onResume() {
        super.onResume()
    }

    // 슬라이더 이동에 따른 가격 범위 텍스트 변경
    private val valueChangeListener: RangeSlider.OnChangeListener =
        RangeSlider.OnChangeListener { slider, value, fromUser ->
            val vStart = slider.values[0]
            val vEnd = slider.values[1]
            var newStart = ""
            var newEnd = ""

            if (value != vEnd) {
                newStart = "${value.toInt()}"
                newEnd = "${vEnd.toInt()}"
            }
            else if (value != vStart) {
                newEnd = "${value.toInt()}"
                newStart = "${vStart.toInt()}"
            }
            else if (vEnd == vStart) {
                newStart = "${value.toInt()}"
                newEnd = "${value.toInt()}"
            }
            if (slider == binding.rsDeposit) {
                binding.tvDepositRange.text = getString(R.string.deposit_price_range, newStart, newEnd)
            }
            if (slider == binding.rsMonthlypay) {
                binding.tvMonthlypayRange.text = getString(R.string.deposit_price_range, newStart, newEnd)
            }

        }

    // 체크박스 체크상태에 따른 밑에 슬라이더 visibility 설정
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
                        binding.layoutMonthlyPriceRange.visibility = View.VISIBLE
                        if (!binding.cbOptionJeonsae.isChecked) {
                            binding.rsDeposit.visibility = View.VISIBLE
                            binding.tvDeposit.visibility = View.VISIBLE
                            binding.tvDepositRange.visibility = View.VISIBLE
                            binding.layoutDepositPriceRange.visibility = View.VISIBLE
                        }
                    }
                    else {
                        payOptionCnt--
                        binding.rsMonthlypay.visibility = View.GONE
                        binding.tvMonthlypay.visibility = View.GONE
                        binding.tvMonthlypayRange.visibility = View.GONE
                        binding.layoutMonthlyPriceRange.visibility = View.GONE
                        if (!binding.cbOptionJeonsae.isChecked) {
                            binding.tvPrice.visibility = View.GONE
                            binding.rsDeposit.visibility = View.GONE
                            binding.tvDeposit.visibility = View.GONE
                            binding.tvDepositRange.visibility = View.GONE
                            binding.layoutDepositPriceRange.visibility = View.GONE
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
                            binding.layoutDepositPriceRange.visibility = View.VISIBLE
                        }
                    }
                    else {
                        payOptionCnt--
                        if (!binding.cbOptionWalsae.isChecked) {
                            binding.tvPrice.visibility = View.GONE
                            binding.rsDeposit.visibility = View.GONE
                            binding.tvDeposit.visibility = View.GONE
                            binding.tvDepositRange.visibility = View.GONE
                            binding.layoutDepositPriceRange.visibility = View.GONE
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
                binding.btnNext.setUsableButton()
            else
                binding.btnNext.setUnUsableButton()
        }
    }

    private fun setOptionChoice() {
        binding.rvFurnitureOption.run {
            adapter = ConditionOptionAdapter(object : ConditionOptionAdapter.SelectOptionInterface {
                override fun select(option: Int) {
                    TODO("Not yet implemented")
                }

                override fun unselect(option: Int) {
                    TODO("Not yet implemented")
                }
            })
            addItemDecoration(ItemDecoration(8, 8))
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
        }
    }

    private fun removeSliderPadding() {
        binding.rsDeposit.setPadding(0, 0, 0, 0)
        binding.rsMonthlypay.setPadding(0, 0, 0, 0)
    }

}