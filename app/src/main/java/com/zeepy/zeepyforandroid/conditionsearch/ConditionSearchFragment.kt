package com.zeepy.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.fragment.app.viewModels
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.google.android.material.slider.RangeSlider
import com.zeepy.zeepyforandroid.conditionsearch.data.CheckBoxModel
import com.zeepy.zeepyforandroid.eunm.Options
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){

    private val viewModel by viewModels<ConditionSearchViewModel>()
    private var buildingTypesCounter = 2
    private var tradeTypesCounter = 2

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

        viewModel.getBuildingTypesObservable()?.let {
            setCheckboxCheckedChangeListener(it)
        }
        viewModel.getTradeTypesObservable()?.let {
            setCheckboxCheckedChangeListener(it)
        }

    }

    private fun setCheckboxCheckedChangeListener(checkboxCollection: Collection<CheckBoxModel>) {
        checkboxCollection.forEach {
            it.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (it.checked) {
                        if (it.id == 1 || it.id == 2) {
                            buildingTypesCounter++
                        } else {
                            tradeTypesCounter++
                        }
                    } else {
                        if (it.id == 1 || it.id == 2) {
                            buildingTypesCounter--
                        } else {
                            tradeTypesCounter--
                        }
                    }
                    if (buildingTypesCounter >= 3 && tradeTypesCounter >= 2) {
                        binding.btnNext.setUsableButton()
                    } else {
                        binding.btnNext.setUnUsableButton()
                    }
                    Log.e("what is tradeTypesCounter", tradeTypesCounter.toString())
                    Log.e("What is buildingTypesCounter", buildingTypesCounter.toString())
                }
            })
        }
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
                    viewModel.selectOption(Options.findOptions(option))
                }

                override fun unselect(option: Int) {
                    viewModel.unselectOption(Options.findOptions(option))
                }
            })
            addItemDecoration(ItemDecoration(8, 8))
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUsableButton()
        }
    }

    private fun removeSliderPadding() {
        binding.rsDeposit.setPadding(0, 0, 0, 0)
        binding.rsMonthlypay.setPadding(0, 0, 0, 0)
    }
}