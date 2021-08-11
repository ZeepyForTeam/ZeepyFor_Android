package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentManageReviewBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyReviewAdapter
import com.zeepy.zeepyforandroid.util.ItemDecoration

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