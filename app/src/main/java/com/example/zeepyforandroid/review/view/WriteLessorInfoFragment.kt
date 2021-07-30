package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding
import com.example.zeepyforandroid.databinding.ItemSpinnerAgeBinding
import com.example.zeepyforandroid.eunm.LessorAge.Companion.findLessorAge
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ReviewNotice

class WriteLessorInfoFragment : BaseFragment<FragmentWriteLessorInfoBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

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
        viewModel.changeCurrentFragment(ReviewNotice.WRITE_LESSOR_DETAIL)

        setSpinner()
        setNextButton()
        enableButton()
        goToReviewHouse()
        selectGender()
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
        binding.groupSelectGender.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId) {
                    binding.toggleMale.id -> viewModel.changeLessorGender(requireContext().getString(R.string.male))
                    binding.toggleFemale.id -> viewModel.changeLessorGender(requireContext().getString(R.string.female))
                }
                Log.e("gender", viewModel.lessorGender.value.toString())
                viewModel.lessorPersonality.value?.let { it1 -> Log.e("tendency", it1) }

            }
        })

    }

    private fun setSpinner() {
        val spinnerAdapter = ArrayAdapter<Int>(requireContext(), R.layout.item_spinner_age, ARRAY_AGE_GROUP)
        binding.spinnerAge.run {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    viewModel.changeLessorAge(findLessorAge((view as AppCompatTextView).text.toString()))

                    Log.e("gender", viewModel.lessorGender.value.toString())
                    viewModel.lessorPersonality.value?.let { it1 -> Log.e("tendency", it1) }
                    Log.e("age", viewModel.lessorAge.value.toString())

                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun enableButton() {
        viewModel.reviewOfLessor.observe(viewLifecycleOwner){
            if (viewModel.checkReviewOfLessor()) {
                binding.btnNext.setUsableButton()
            } else {
                binding.btnNext.setUnUsableButton()
            }
        }
    }

    private fun goToReviewHouse() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_writeLessorInfoFragment_to_houseReviewFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        binding.etDetailLessorInfo.text.clear()
    }

    companion object {
        private val ARRAY_AGE_GROUP = arrayListOf(10,20,30,40,50,60,70,80,90)
    }
}