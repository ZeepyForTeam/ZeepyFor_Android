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
import com.zeepy.zeepyforandroid.eunm.LessorAge.Companion.findLessorAge
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.zeepy.zeepyforandroid.util.ReviewNotice

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
        viewModel.changeCurrentFragment(ReviewNotice.WRITE_LESSOR_DETAIL)

        setSpinner()
        setNextButton()
        enableButton()
        goToReviewHouse()
        selectGender()
    }

    override fun onResume() {
        super.onResume()
        viewModel.lessorAge.value?.values?.find { it == 0 }?.let { binding.spinnerAge.setSelection(it) }

        setSpinner()
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
        ArrayAdapter.createFromResource(requireContext(), R.array.lessor_age_values, R.layout.item_spinner_age).also { spinnerAdapter ->
            binding.spinnerAge.run {
                adapter = spinnerAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        viewModel.changeLessorAge(mapOf(findLessorAge((view as AppCompatTextView).text.toString()) to position))
                        Log.e("gender", viewModel.lessorGender.value.toString())
                        viewModel.lessorPersonality.value?.let { it1 -> Log.e("tendency", it1) }
                        Log.e("age", viewModel.lessorAge.value.toString())

                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun enableButton() {
        viewModel.reviewOfLessor.observe(viewLifecycleOwner){
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
            Navigation.findNavController(binding.root).navigate(R.id.action_writeLessorInfoFragment_to_houseReviewFragment)
        }
    }
}