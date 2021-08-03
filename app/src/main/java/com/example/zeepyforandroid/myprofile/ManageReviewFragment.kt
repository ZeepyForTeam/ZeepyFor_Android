package com.example.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentManageReviewBinding
import com.example.zeepyforandroid.myprofile.adapter.MyReviewAdapter
import com.example.zeepyforandroid.review.view.HousePictureFragment
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.ItemDecoration

class ManageReviewFragment: BaseFragment<FragmentManageReviewBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageReviewBinding {
        return FragmentManageReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        val myReviewAdapter = MyReviewAdapter()
        binding.rvReviewList.run {
            adapter = myReviewAdapter
            addItemDecoration(ItemDecoration(8, 0))
        }
    }


}