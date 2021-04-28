package com.example.zeepyforandroid.review.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
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
        setToolbar()
        setNextButton()
    }

    fun setToolbar() {
        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    fun setNextButton(){
        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }
}