package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding
import com.zeepy.zeepyforandroid.enum.LessorAge
import com.zeepy.zeepyforandroid.enum.LessorAge.Companion.findLessorAge
import com.zeepy.zeepyforandroid.enum.LessorGender.Companion.findGender
import com.zeepy.zeepyforandroid.review.AgeSelectListener
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel

class WriteLessorInfoFragment : BaseFragment<FragmentWriteLessorInfoBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteLessorInfoBinding {
        return FragmentWriteLessorInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setNextButton()
        enableButton()
        goToReviewHouse()
        selectGender()
        showLessorAgeInfo()
    }

    override fun onResume() {
        super.onResume()
        binding.groupSelectGender.clearCheck()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
        }
    }

    private fun selectGender() {
        binding.groupSelectGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.toggleMale.id -> viewModel.changeLessorGender(findGender(R.string.male))
                binding.toggleFemale.id -> viewModel.changeLessorGender(findGender(R.string.female))
            }
            viewModel.lessorPersonality.value?.let { it1 -> Log.e("tendency", it1) }
        }
    }

    private fun enableButton() {
        viewModel.reviewOfLessor.observe(viewLifecycleOwner) {
            checkInputAll()
        }
        viewModel.lessorGender.observe(viewLifecycleOwner) {
            checkInputAll()
        }
    }

    private fun checkInputAll() {
        if (viewModel.checkInputEveryLessorInfo()) {
            binding.btnNext.setUsableButton()
        } else {
            binding.btnNext.setUnUsableButton()
        }
    }

    private fun goToReviewHouse() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_writeLessorInfoFragment_to_houseReviewFragment)
        }
    }

    private fun showLessorAgeInfo() {
        binding.tvAgeIntegerValue.setOnClickListener {
            val bottom = LessorAgeBottomSheetFragment(object : AgeSelectListener {
                override fun selectAge(lessorAgeGroup: LessorAge) {
                    setSelectedLessorAge(lessorAgeGroup.age)
                    viewModel.changeLessorAge(lessorAgeGroup.name)
                }
            })
            bottom.show(childFragmentManager, this.tag)
        }
    }

    private fun setSelectedLessorAge(age: String?) {
        binding.tvAgeIntegerValue.text = when (age) {
            LessorAge.UNKNOWN.age -> "00"
            else -> age?.substring(0, 2)
        }
    }
}