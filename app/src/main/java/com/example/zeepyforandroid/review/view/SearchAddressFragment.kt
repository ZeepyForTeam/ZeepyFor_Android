package com.example.zeepyforandroid.review.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSearchAddressBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel

class SearchAddressFragment : BaseFragment<FragmentSearchAddressBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = {requireParentFragment().requireParentFragment()})

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchAddressBinding {
        return FragmentSearchAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        Log.e(viewModel.toString(), viewModel.toString())

        setNextButton()
        setEnableButton()
        invisibleNotice()
        goToWriteDetailAddress()
    }

    private fun setNextButton(){
        binding.layoutSearchAddress.btnNext.run {
            setText("다음으로")
        }
    }

    private fun invisibleNotice() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.visibility = View.GONE
    }

    private fun setEnableButton() {
        viewModel.addressSearchQuery.observe(viewLifecycleOwner){
//            binding.layoutSearchAddress.btnNext.apply {
//                if(viewModel.checkInputAddressQuery()) {
//                    usableButton()
//                    goToWriteDetailAddress()
//                } else {
//                    unUseableButton()
//                }
//            }
        }
    }

    private fun goToWriteDetailAddress() {
        binding.layoutSearchAddress.btnNext.setOnClickListener{
            viewModel.changeAddressSearchgQuery(binding.layoutSearchAddress.etSearchAddress.text.toString())
            Navigation.findNavController(binding.root).navigate(R.id.action_searchAddressFragment_to_writeDetailAddressFragment)
        }
    }
}