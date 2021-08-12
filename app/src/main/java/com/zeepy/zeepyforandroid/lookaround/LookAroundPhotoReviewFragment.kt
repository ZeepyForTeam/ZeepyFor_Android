package com.zeepy.zeepyforandroid.lookaround


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundPhotoReviewBinding
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.util.ItemDecoration


class LookAroundPhotoReviewFragment: BaseFragment<FragmentLookaroundPhotoReviewBinding>() {
    private lateinit var photoReviewAdapter: PhotoReviewAdapter
    private lateinit var photos: MutableList<PictureModel>

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundPhotoReviewBinding {
        return FragmentLookaroundPhotoReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        photoReviewAdapter = PhotoReviewAdapter()
        binding.rvPhotoReview.run {
            adapter = photoReviewAdapter
            addItemDecoration(ItemDecoration(8, 8))
        }
    }
}