package com.example.zeepyforandroid.conditionsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSearchByConditionBinding


class ConditionSearchFragment : BaseFragment<FragmentSearchByConditionBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchByConditionBinding {
        return FragmentSearchByConditionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchOptions()

    }

    override fun onResume() {
        super.onResume()
        binding.buildingtypeGroup.clearCheck()
        binding.paytypeGroup.clearCheck()
    }

//    private fun setTopNotice() {
//        val parent = (parentFragment as NavHostFragment).parentFragment
//        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
//        notice?.visibility = View.GONE
//
//        val span = binding.tvReviewNotice.text.toSpannable()
//        val typeface = Typeface.create(
//            ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold),
//            Typeface.NORMAL)
//        span.setSpan(CustomTypefaceSpan(typeface), 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//        span.setSpan(CustomTypefaceSpan(typeface), 15, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//    }

//    private fun setReviewChoice() {
//        binding.rvReviewChoice.run {
//            adapter = ReviewChoiceAdapter{
//                enableNextButton(it)
//            }
//            addItemDecoration(ItemDecoration(13, 0))
//        }
//    }

//    private fun setOptionChoice() {
//        binding.rvOption.run {
//            adapter = ReviewOptionAdapter()
//            addItemDecoration(ItemDecoration(8, 8))
//        }
//    }
//
//    private fun setNextButton() {
//        binding.btnNext.run {
//            setText("다음으로")
//            unUseableButton()
//        }
//    }

//    private fun enableNextButton(map:Map<Int,Int>) {
//        if(map.size == 4) {
//            binding.btnNext.usableButton()
//            goToWriteHouseInfo()
//        } else {
//            binding.btnNext.unUseableButton()
//        }
//    }

    private fun setSearchOptions() {
        binding.buildingtypeGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.radiobtnOptionOneroom.id -> {
                    binding.radiobtnOptionTworoom.isChecked = false
                    binding.radiobtnOptionOfficetel.isChecked = false
                }
                binding.radiobtnOptionTworoom.id -> {
                    binding.radiobtnOptionOneroom.isChecked = false
                    binding.radiobtnOptionOfficetel.isChecked = false
                }
                binding.radiobtnOptionOfficetel.id -> {
                    binding.radiobtnOptionOneroom.isChecked = false
                    binding.radiobtnOptionTworoom.isChecked = false
                }
            }

        }
        binding.paytypeGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.radiobtnOptionWalsae.id -> {
                    binding.radiobtnOptionJeonsae.isChecked = false
                    binding.radiobtnOptionMaemae.isChecked = false
                }
                binding.radiobtnOptionJeonsae.id -> {
                    binding.radiobtnOptionWalsae.isChecked = false
                    binding.radiobtnOptionMaemae.isChecked = false
                }
                binding.radiobtnOptionMaemae.id -> {
                    binding.radiobtnOptionWalsae.isChecked = false
                    binding.radiobtnOptionJeonsae.isChecked = false
                }
            }

        }
    }
}