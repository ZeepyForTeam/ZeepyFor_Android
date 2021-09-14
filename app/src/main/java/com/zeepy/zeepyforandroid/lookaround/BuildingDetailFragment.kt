package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentBuildingDetailBinding
import com.zeepy.zeepyforandroid.enum.DealType
import com.zeepy.zeepyforandroid.enum.Options
import com.zeepy.zeepyforandroid.enum.RoomCount
import com.zeepy.zeepyforandroid.lookaround.viewmodel.BuildingDetailViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuildingDetailFragment: BaseFragment<FragmentBuildingDetailBinding>() {
    private val viewModel by viewModels<BuildingDetailViewModel>()
    private val args: BuildingDetailFragmentArgs by navArgs()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changeSummary(args.buildingSummaryModel)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBuildingDetailBinding {
        return FragmentBuildingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // 현재 빌딩 id로 뷰모델 데이터 업데이트
        viewModel.changeBuildingId(args.buildingSummaryModel.id)

        setToolbar()
        renderOptions()
        renderPictures()
        renderRoomCount()
        renderPaymentType()
        renderBuildingAddress()
        renderCommunicationTendency()
        setReviewContents()


    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle(args.buildingSummaryModel.buildingName)
            setLookaroundBuildingTitle()
            setBackButton {
                findNavController().popBackStack()
            }
            setScrapButton {
                if(binding.checkboxScrap.isChecked) {
                    viewModel.scrapBuilding()
                } else {
                    viewModel.cancelScrapBuilding()
                }
            }
        }
    }

    // FIXME: Change the rendering functions to Data Binding Later

    private fun setReviewContents() {
        args.buildingSummaryModel.reviews.let {
            if (it.isNullOrEmpty()) {
                binding.btnShowAllReviews.visibility = View.GONE
                binding.layoutSampleReview.root.visibility = View.GONE
                binding.layoutNoReview.visibility = View.VISIBLE

                binding.tvGotoWriteReview.setOnClickListener {
                    // check for login status and then navigate
                    if (userPreferenceManager.fetchIsAlreadyLogin()) {
                        requireParentFragment().findNavController().navigate(R.id.action_buildingDetailFragment_to_reviewFrameFragment)
                    } else {
                        Toast.makeText(context, "로그인을 해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                binding.layoutNoReview.visibility = View.GONE
                binding.btnShowAllReviews.visibility = View.VISIBLE
                binding.layoutSampleReview.root.visibility = View.VISIBLE
                binding.layoutSampleReview.apply {
                    tvReviewerName.text = String.format(resources.getString(R.string.review_by_whom), it[0].user.name)
                    tvLessorReviewOneLiner.text = it[0].lessorGender
                    tvLessorReviewMsg.text = it[0].lessorReview
                    tvSoundInsulationRating.text = it[0].soundInsulation
                    tvPestRating.text = it[0].pest
                    tvSunlightRating.text = it[0].lightning
                    tvWaterpressureRating.text = it[0].waterPressure
                    tvHouseReviewMsg.text = it[0].review
                    tvOverallEvaluationMsg.text = it[0].totalEvaluation
                }
                binding.btnShowAllReviews.setOnClickListener {
                    val action = BuildingDetailFragmentDirections.actionBuildingDetailFragmentToBuildingAllReviewsFragment(
                        args.buildingSummaryModel
                    )
                    findNavController().navigate(action)
                }
            }
        }
    }

    // FIXME: Find alternative for getting sums of these
    private fun renderCommunicationTendency() {
        var businessTotal: Int = 0
        var kindTotal: Int = 0
        var grazeTotal: Int = 0
        var softyTotal: Int = 0
        var badTotal: Int = 0

        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                it.forEach { review ->
                    when (review.communcationTendency) {
                        "BUSINESS" -> businessTotal++
                        "KIND" -> kindTotal++
                        "GRAZE" -> grazeTotal++
                        "SOFTY" -> softyTotal++
                        "BAD" -> badTotal++
                    }
                }
                binding.tvBusinessLikes.apply {
                    text = businessTotal.toString()
                    if (businessTotal > 0) {
                        setTextColor(ContextCompat.getColor(context, R.color.zeepy_blue_5f))
                    }
                }
                binding.tvKindLikes.apply {
                    text = kindTotal.toString()
                    if (kindTotal > 0) {
                        setTextColor(ContextCompat.getColor(context, R.color.zeepy_blue_5f))
                    }
                }
                binding.tvGrazeLikes.apply {
                    text = grazeTotal.toString()
                    if (grazeTotal > 0) {
                        setTextColor(ContextCompat.getColor(context, R.color.zeepy_blue_5f))
                    }
                }
                binding.tvSoftyLikes.apply {
                    text = softyTotal.toString()
                    if (softyTotal > 0) {
                        setTextColor(ContextCompat.getColor(context, R.color.zeepy_blue_5f))
                    }
                }
                binding.tvBadLikes.apply {
                    text = badTotal.toString()
                    if (badTotal > 0) {
                        setTextColor(ContextCompat.getColor(context, R.color.zeepy_blue_5f))
                    }
                }
            }
        }
    }

    private fun renderBuildingAddress() {
        binding.tvAddress.text = args.buildingSummaryModel.shortAddress
    }

    private fun renderPaymentType() {
        args.buildingSummaryModel.buildingDeals.let {
            if (!it.isNullOrEmpty()) {
                binding.tvDealTypeContent.text = resources.getString(DealType.findDealTypeFromString(it[0].dealType))
            }
        }
    }

    private fun renderRoomCount() {
        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                binding.tvRoomCount.text = resources.getString(RoomCount.findRoomCountFromString(it[0].roomCount))
            }
        }
    }

    private fun renderOptions() {
        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                if (!it[0].furnitures.isNullOrEmpty()) {
                    binding.tvCharacteristicsContent.text = args.buildingSummaryModel.reviews[0].furnitures.joinToString(separator = ", ") { furniture ->
                        resources.getString(Options.getOptionFromString(furniture))
                    }
                }
            }
        }
    }

    // FIXME: Consider using an adapter..
    private fun renderPictures() {
        args.buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                if (!it[0].imageUrls.isNullOrEmpty()) {
                    it[0].imageUrls.let { images ->
                        val imagesCount = images.size
                        if (!it.isNullOrEmpty()) {
                            when (imagesCount) {
                                1 -> {
                                    binding.image1.load(images[0])
                                }
                                2 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                }
                                3 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                    binding.image3.load(images[2])
                                }
                                4 -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                    binding.image3.load(images[2])
                                    binding.imageAdd.load(images[3])
                                } else -> {
                                    binding.image1.load(images[0])
                                    binding.image2.load(images[1])
                                    binding.image3.load(images[2])
                                    binding.imageAdd.load(images[3])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}