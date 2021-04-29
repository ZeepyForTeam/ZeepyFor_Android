package com.example.zeepyforandroid.review.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding

class WriteLessorInfoFragment : BaseFragment<FragmentWriteLessorInfoBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteLessorInfoBinding {
        return FragmentWriteLessorInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView(){
        binding.toolbar.run {
            setTitle("리뷰작성")
        }
    }
}