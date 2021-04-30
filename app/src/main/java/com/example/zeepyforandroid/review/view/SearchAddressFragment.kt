package com.example.zeepyforandroid.review.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSearchAddressBinding
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel


class SearchAddressFragment : BaseFragment<FragmentSearchAddressBinding>() {
    private val viewModel: WriteReviewViewModel by activityViewModels()

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
        setNextButton()
        setEnableButton()
        invisibleNotice()
    }

    private fun setNextButton(){
        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }

    private fun invisibleNotice() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.visibility = View.GONE
    }

    private fun setEnableButton() {
        viewModel.addressSearchQuery.observe(viewLifecycleOwner){
            if(viewModel.checkInputAddressQuery()) {
                binding.btnNext.usableButton()
                goToWriteDetailAddress()
            } else {
                binding.btnNext.unUseableButton()
            }
        }
    }

    private fun goToWriteDetailAddress() {
        binding.btnNext.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_searchAddressFragment_to_writeDetailAddressFragment)
        }
    }
}