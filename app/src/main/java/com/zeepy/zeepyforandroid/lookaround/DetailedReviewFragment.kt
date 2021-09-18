package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundDetailedReviewBinding
import com.zeepy.zeepyforandroid.enum.CommunityTendency
import com.zeepy.zeepyforandroid.enum.LessorAge
import com.zeepy.zeepyforandroid.enum.Preference
import com.zeepy.zeepyforandroid.lookaround.adapter.PhotoReviewAdapter
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.ZeepyStringBuilder

class DetailedReviewFragment: BaseFragment<FragmentLookaroundDetailedReviewBinding>() {
    private val args: DetailedReviewFragmentArgs by navArgs()
    private lateinit var photoReviewAdapter: PhotoReviewAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundDetailedReviewBinding {
        return FragmentLookaroundDetailedReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        initRecyclerView()
        render()
    }

    private fun initRecyclerView() {
        photoReviewAdapter = PhotoReviewAdapter()
        binding.rvSubmittedPhotos.run {
            adapter = photoReviewAdapter
            addItemDecoration(ItemDecoration(8, 8))
        }
        args.responseReview.imageUrls.let {
            photoReviewAdapter.submitList(it)
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setLookaroundBuildingTitle()
            setTitle("금성토성빌 상세리뷰")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }

    private fun render() {
        args.responseReview.let { review ->
            binding.tvReviewerName.text = getString(R.string.review_by_whom, review.user.name)
            binding.tvSummaryLessorReview.text = ZeepyStringBuilder.buildLessorAgeAndGenderStmt(LessorAge.findLessorAgeFromLiteralString(review.lessorAge), review.lessorGender)
            binding.tvLessorCommunicationReview.text = getString(CommunityTendency.findTendencyFromString(review.communcationTendency))
            binding.tvSoundInsulationRating.text = getString(Preference.getIdFromString(review.soundInsulation))
            binding.tvPestRating.text = getString(Preference.getIdFromString(review.pest))
            binding.tvSunlightRating.text = getString(Preference.getIdFromString(review.lightning))
            binding.tvWaterpressureRating.text = getString(Preference.getIdFromString(review.waterPressure))

            binding.tvLessorReviewContent.text = review.lessorReview
            binding.tvHouseReviewContent.text = review.review

        }
    }
}