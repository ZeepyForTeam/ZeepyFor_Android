package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ReviewNotice

class WriteLessorInfoFragment : BaseFragment<FragmentWriteLessorInfoBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()

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
        checkSingleSex()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
        }
    }

    private fun checkSingleSex(){
        viewModel.manCheck.observe(viewLifecycleOwner){manChecking ->
            if (manChecking) {
                viewModel.womenCheck.value = !manChecking
            }
        }
        viewModel.womenCheck.observe(viewLifecycleOwner){ womenChecking ->
            if (womenChecking){
                viewModel.manCheck.value = !womenChecking
            }
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

    companion object {
        private val ARRAY_AGE_GROUP = arrayListOf(10,20,30,40,50,60,70,80,90)
    }
}