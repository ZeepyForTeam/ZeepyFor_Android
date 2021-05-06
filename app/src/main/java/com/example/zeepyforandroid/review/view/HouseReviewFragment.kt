package com.example.zeepyforandroid.review.view

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
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentHouseReviewBinding
import com.example.zeepyforandroid.review.view.adapter.ReviewChoiceAdapter
import com.example.zeepyforandroid.review.view.adapter.ReviewOptionAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.ReviewNotice

class HouseReviewFragment : BaseFragment<FragmentHouseReviewBinding>(){
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHouseReviewBinding {
        return FragmentHouseReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.changeCurrentFragment(ReviewNotice.CHECK_HOUSE_CONDITION)
        setReviewChoice()
        setRoomTypeChoice()
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
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(),R.font.nanum_square_round_extrabold),Typeface.NORMAL)
        span.setSpan(CustomTypefaceSpan(typeface), 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        span.setSpan(CustomTypefaceSpan(typeface), 15, 21, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

    }

    private fun setReviewChoice() {
        binding.rvReviewChoice.run {
            adapter = ReviewChoiceAdapter{
                enableNextButton(it)
            }
            addItemDecoration(ItemDecoration(13, 0))
        }
    }

    private fun setOptionChoice() {
        binding.rvOption.run {
            adapter = ReviewOptionAdapter()
            addItemDecoration(ItemDecoration(8, 8))
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }

    private fun enableNextButton(map:Map<Int,Int>) {
        if(map.size == 4) {
            binding.btnNext.usableButton()
            goToWriteHouseInfo()
        } else {
            binding.btnNext.unUseableButton()
        }
    }

    private fun setRoomTypeChoice() {
        binding.roomtypeGroup.setOnCheckedChangeListener { group, checkId ->
            when (checkId) {
                binding.radiobtnOneRoom.id -> {
                    binding.radiobtnTwoRoom.isChecked = false
                    binding.radiobtnThreeRoom.isChecked = false
                }
                binding.radiobtnTwoRoom.id -> {
                    binding.radiobtnOneRoom.isChecked = false
                    binding.radiobtnThreeRoom.isChecked = false
                }
                binding.radiobtnThreeRoom.id -> {
                    binding.radiobtnTwoRoom.isChecked = false
                    binding.radiobtnOneRoom.isChecked = false
                }
            }

        }
    }

    private fun goToWriteHouseInfo() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_houseReviewFragment_to_writeHouseInfoFragment)
        }
    }
}