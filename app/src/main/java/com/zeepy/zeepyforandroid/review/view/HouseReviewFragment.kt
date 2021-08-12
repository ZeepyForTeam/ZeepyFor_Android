package com.zeepy.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.adapter.ConditionOptionAdapter
import com.zeepy.zeepyforandroid.databinding.FragmentHouseReviewBinding
import com.zeepy.zeepyforandroid.eunm.Options.Companion.findOptions
import com.zeepy.zeepyforandroid.eunm.Preference.Companion.findPreference
import com.zeepy.zeepyforandroid.eunm.RoomCount.Companion.findRoomCount
import com.zeepy.zeepyforandroid.review.view.adapter.ReviewChoiceAdapter
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.ReviewNotice

class HouseReviewFragment : BaseFragment<FragmentHouseReviewBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHouseReviewBinding {
        return FragmentHouseReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.changeCurrentFragment(ReviewNotice.CHECK_HOUSE_CONDITION)
        Log.e(viewModel.toString(), viewModel.toString())

        setRoomTypeChoice()
        setReviewChoice()
        setOptionChoice()
        setNextButton()
        goToWriteHouseInfo()
        setTopNotice()
    }

    override fun onResume() {
        super.onResume()
        binding.roomtypeGroup.clearCheck()
    }

    private fun setTopNotice() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.visibility = View.GONE

        val span = binding.tvReviewNotice.text.toSpannable()
        val typeface = Typeface.create(
            ResourcesCompat.getFont(
                requireContext(),
                R.font.nanum_square_round_extrabold
            ), Typeface.NORMAL
        )
        span.setSpan(CustomTypefaceSpan(typeface), 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        span.setSpan(CustomTypefaceSpan(typeface), 15, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun setReviewChoice() {
        binding.rvReviewChoice.run {
            adapter = ReviewChoiceAdapter { map ->
                map.forEach { evaluation ->
                    viewModel.addReviewPreference(evaluation.key, findPreference(evaluation.value))
                    Log.e("viewModel evaluation", "${findPreference(evaluation.value)}")
                }
                viewModel.reviewPreference.value?.let { enableNextButton(it) }
            }
            addItemDecoration(ItemDecoration(13, 0))
        }
    }

    private fun setRoomTypeChoice() {
        binding.roomtypeGroup.setOnCheckedChangeListener { group, checkId ->
            when(checkId) {
                binding.radiobtnOneRoom.id -> viewModel.changeRoomType(findRoomCount(R.string.roomcount_one))
                binding.radiobtnTwoRoom.id -> viewModel.changeRoomType(findRoomCount(R.string.roomcount_two))
                binding.radiobtnThreeRoom.id -> viewModel.changeRoomType(findRoomCount(R.string.roomcount_threeormore))
            }
            viewModel.reviewPreference.value?.let { enableNextButton(it) }
            Log.e("roomtype", viewModel.roomType.value.toString())
        }
    }

    private fun setOptionChoice() {
        binding.rvOption.run {
            adapter = ConditionOptionAdapter(object : ConditionOptionAdapter.SelectOptionInterface {
                override fun select(option: Int) {
                    viewModel.selectOption(findOptions(option))
                    Log.e("option", "${viewModel.selectedOptionList.value}")
                }

                override fun unselect(option: Int) {
                    viewModel.unselectOption(findOptions(option))
                    Log.e("option", "${viewModel.selectedOptionList.value}")

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


    private fun enableNextButton(map: Map<String, String>) {
        if (map.size == 4 && !viewModel.roomType.value.isNullOrEmpty()) {
            binding.btnNext.setUsableButton()
            goToWriteHouseInfo()
        } else {
            binding.btnNext.setUnUsableButton()
        }
    }

    private fun goToWriteHouseInfo() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_houseReviewFragment_to_writeHouseInfoFragment)
        }
    }
}