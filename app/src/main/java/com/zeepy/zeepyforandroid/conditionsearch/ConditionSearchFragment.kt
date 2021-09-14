package com.zeepy.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.slider.LabelFormatter.LABEL_GONE
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentSearchByConditionBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.google.android.material.slider.RangeSlider
import com.zeepy.zeepyforandroid.conditionsearch.data.ConditionSetModel
import com.zeepy.zeepyforandroid.enum.Options
import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel
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

        setToolbar()
        setRangeSliderOnChangeListener()
        setOptionChoice()
        setNextButton()
        initSliders()

    }

    private fun setToolbar() {
        binding.conditionSearchToolbar.apply {
            setTitle("조건 검색")
            setBackButton {
                findNavController().popBackStack()
            }
        }
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
            onClick {
                // Go back to 둘러보기 with filters applied
                // Send the filter conditions to parent fragment(LookAroundFragment) and the parent fragment will refresh the list.
                // NOTE: If filter conditions are set at the point of changing address or tendency filters,
                //   when address change -> get new list for address and then apply filter conditions.
                //   when tendency filters change -> the second filtering is done on the first filtered list.

                // Change the value that needs to be passed back to LookAroundFragment

                val navController = findNavController()
                navController.previousBackStackEntry?.savedStateHandle?.set("conditions", getCurrentlyCheckedConditions())
                navController.popBackStack()
            }
        }
    }

    private fun getCurrentlyCheckedConditions(): ConditionSetModel {
        // BuildingType
        val buildingType: String = when (binding.rgBuildingType.checkedRadioButtonId) {
            R.id.rb_building_type_all -> "ALL"
            R.id.rb_rowhouse -> "ROWHOUSE"
            R.id.rb_officetel -> "OFFICETEL"
            else -> "ALL"
        }
        // DealType
        val dealType: String = when (binding.rgDealType.checkedRadioButtonId) {
            R.id.rb_option_trade_type_all -> "ALL"
            R.id.rb_option_walsae -> "MONTHLY"
            R.id.rb_option_jeonsae -> "JEONSE"
            else -> "ALL"
        }
        // DepositPay
        val depositPayStart: Int = when (binding.rsDeposit.values[0]) {
            0F -> 0
            1F -> 500
            2F -> 1000
            3F -> 2500
            4F -> 5000
            5F -> 10000
            6F -> Int.MAX_VALUE
            else -> -1
        }
        val depositPayEnd: Int = when (binding.rsDeposit.values[1]) {
            0F -> 0
            1F -> 500
            2F -> 1000
            3F -> 2500
            4F -> 5000
            5F -> 10000
            6F -> Int.MAX_VALUE
            else -> -1
        }

        // MonthlyPay
        val monthlyPayStart: Int = when (binding.rsMonthlypay.values[0]) {
            0F -> 0
            1F -> 25
            2F -> 50
            3F -> 75
            4F -> 100
            5F -> 125
            6F -> Int.MAX_VALUE
            else -> -1
        }
        val monthlyPayEnd: Int = when (binding.rsMonthlypay.values[1]) {
            0F -> 0
            1F -> 25
            2F -> 50
            3F -> 75
            4F -> 100
            5F -> 125
            6F -> Int.MAX_VALUE
            else -> -1
        }

        // Options
        val options: List<String> = viewModel.selectedOptionList.value?.toList()!!

        return ConditionSetModel(buildingType, dealType, depositPayStart, depositPayEnd, monthlyPayStart, monthlyPayEnd, options)
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