package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding
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
    }

    override fun onResume() {
        super.onResume()
        binding.groupSelectSex.clearCheck()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            setUnUsableButton()
        }
    }

    private fun setSpinner() {
        val spinnerAdapter = ArrayAdapter<Int>(requireContext(), android.R.layout.simple_list_item_1, ARRAY_AGE_GROUP)
        binding.spinnerAge.run {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
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