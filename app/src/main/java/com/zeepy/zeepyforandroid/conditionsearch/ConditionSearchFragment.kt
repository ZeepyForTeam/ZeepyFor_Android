package com.zeepy.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){

    private val viewModel by viewModels<ConditionSearchViewModel>()

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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setRangeSliderOnChangeListener()
        setOptionChoice()
        setNextButton()
        removeSliderPadding()

    }

    private fun setRangeSliderOnChangeListener() {
        binding.rsMonthlypay.addOnChangeListener(valueChangeListener)
        binding.rsDeposit.addOnChangeListener(valueChangeListener)
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