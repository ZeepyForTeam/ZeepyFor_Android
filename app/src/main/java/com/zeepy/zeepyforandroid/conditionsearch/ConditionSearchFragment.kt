package com.zeepy.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.slider.LabelFormatter.LABEL_GONE
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.google.android.material.slider.RangeSlider
import com.zeepy.zeepyforandroid.enum.Options
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){

    private val viewModel by viewModels<ConditionSearchViewModel>()

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
        initSliders()

    }

    private fun setRangeSliderOnChangeListener() {
        binding.rsMonthlypay.addOnChangeListener(valueChangeListener)
        binding.rsDeposit.addOnChangeListener(valueChangeListener)
    }


    private fun stepToPrice(stepValue: Float, payType: String): String {
        var price: String = ""
        if (payType == "deposit") {
            when (stepValue) {
                1F -> price = "500만"
                2F -> price = "1,000만"
                3F -> price = "2,500만"
                4F -> price = "5,000만"
                5F -> price = "1억만"
            }
        } else {
            when (stepValue) {
                1F -> price = "25만"
                2F -> price = "50만"
                3F -> price = "75만"
                4F -> price = "100만"
                5F -> price = "125만"
            }
        }
        return price
    }

    // 슬라이더 이동에 따른 가격 범위 텍스트 변경
    // TODO: ViewModel Data Binding
    private val valueChangeListener: RangeSlider.OnChangeListener =
        RangeSlider.OnChangeListener { slider, value, fromUser ->
            val vStart = slider.values[0]
            val vEnd = slider.values[1]
            var newStart = ""
            var newEnd = ""

            if (slider == binding.rsDeposit) {
                if (value != vEnd) {
                    newStart = stepToPrice(value, "deposit")
                    newEnd = stepToPrice(vEnd, "deposit")
                }
                else if (value != vStart) {
                    newEnd = stepToPrice(value, "deposit")
                    newStart = stepToPrice(vStart, "deposit")
                }
                // FIXME: Unreachable block
                else if (vEnd == vStart) {
                    newStart = stepToPrice(value, "deposit")
                    newEnd = stepToPrice(value, "deposit")
                }
                if (vStart == 0F) {
                    if(vEnd == 6F) {
                        binding.tvDepositRange.text = "전체"
                    } else
                        binding.tvDepositRange.text = getString(R.string.deposit_price_to, newEnd)
                } else if (vEnd == 6F) {
                    if (vStart == 0F) {
                        binding.tvDepositRange.text = "전체"
                    } else
                        binding.tvDepositRange.text = getString(R.string.deposit_price_from, newStart)
                } else {
                    binding.tvDepositRange.text = getString(R.string.deposit_price_range, newStart, newEnd)
                }
            }
            else if (slider == binding.rsMonthlypay) {
                if (value != vEnd) {
                    newStart = stepToPrice(value, "monthly")
                    newEnd = stepToPrice(vEnd, "monthly")
                }
                else if (value != vStart) {
                    newEnd = stepToPrice(value, "monthly")
                    newStart = stepToPrice(vStart, "monthly")
                }
                // FIXME: Unreachable block
                else if (vEnd == vStart) {
                    newStart = stepToPrice(value, "monthly")
                    newEnd = stepToPrice(value, "monthly")
                }
                if (vStart == 0F) {
                    if(vEnd == 6F) {
                        binding.tvMonthlypayRange.text = "전체"
                    } else
                        binding.tvMonthlypayRange.text = getString(R.string.monthly_price_to, newEnd)
                } else if (vEnd == 6F) {
                    if (vStart == 0F) {
                        binding.tvMonthlypayRange.text = "전체"
                    } else
                        binding.tvMonthlypayRange.text = getString(R.string.monthly_price_from, newStart)
                } else {
                    binding.tvMonthlypayRange.text = getString(R.string.monthly_price_range, newStart, newEnd)
                }
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

    private fun initSliders() {
        binding.rsDeposit.setPadding(0, 0, 0, 0)
        binding.rsMonthlypay.setPadding(0, 0, 0, 0)
        binding.rsDeposit.setMinSeparationValue(1F)
        binding.rsMonthlypay.setMinSeparationValue(1F)
        binding.rsDeposit.labelBehavior = LABEL_GONE
        binding.rsMonthlypay.labelBehavior = LABEL_GONE
    }
}