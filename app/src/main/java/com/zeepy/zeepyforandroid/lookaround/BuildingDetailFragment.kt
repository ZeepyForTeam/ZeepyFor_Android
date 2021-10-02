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
import com.google.gson.Gson
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentBuildingDetailBinding
import com.zeepy.zeepyforandroid.enum.*
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.viewmodel.BuildingDetailViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.ZeepyStringBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuildingDetailFragment: BaseFragment<FragmentBuildingDetailBinding>() {
    private val viewModel by viewModels<BuildingDetailViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var buildingSummaryModel: BuildingSummaryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.get("buildingSummaryModel") != null) {
            viewModel.changeSummary(requireArguments().get("buildingSummaryModel") as BuildingSummaryModel)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBuildingDetailBinding {
        return FragmentBuildingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildingSummaryModel = if (arguments?.get("buildingSummaryModelJson") != "fromLookAround") {
            Gson().fromJson(arguments?.get("buildingSummaryModelJson") as String, BuildingSummaryModel::class.java)
        } else {
            arguments?.get("buildingSummaryModel") as BuildingSummaryModel
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        subscribeObservers()

        // 현재 빌딩 id로 뷰모델 데이터 업데이트
        viewModel.changeBuildingId(buildingSummaryModel.id)
        viewModel.setBuildingsUserLikes()

        setToolbar()
        renderOptions()
        renderPictures()
        renderBuildingType()
        renderPaymentType()
        renderBuildingAddress()
        renderCommunicationTendency()
        setReviewContents()
    }

    private fun subscribeObservers() {
        viewModel.buildingsUserLikes.observe(viewLifecycleOwner) {
            viewModel.checkIfUserLikesBuilding()
        }
        viewModel.isUserLike.observe(viewLifecycleOwner) {
            if (it) {
                binding.toolbar.apply {
                    binding.checkboxScrap.isChecked = true
                }
            } else {
                binding.toolbar.apply {
                    binding.checkboxScrap.isChecked = false
                }
            }
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle(buildingSummaryModel.buildingName)
            setLookaroundBuildingTitle()
            setBackButton {
                findNavController().popBackStack()
            }
            setScrapButton {
                if (binding.checkboxScrap.isChecked) {
                    viewModel.scrapBuilding()
                } else {
                    viewModel.cancelScrapBuilding()
                }
            }
        }
    }

    // FIXME: Change the rendering functions to Data Binding Later

    private fun setReviewContents() {
        buildingSummaryModel.reviews.let {
            if (it.isNullOrEmpty()) {
                binding.btnShowAllReviews.visibility = View.GONE
                binding.layoutRepReview.root.visibility = View.GONE
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
                binding.layoutRepReview.root.visibility = View.VISIBLE
                binding.layoutRepReview.apply {
                    tvReviewerName.text = String.format(resources.getString(R.string.review_by_whom), it[0].user.name)
                    tvLessorReviewOneLiner.text = ZeepyStringBuilder.buildLessorAgeAndGenderStmt(LessorAge.findLessorAgeFromLiteralString(it[0].lessorAge), it[0].lessorGender)
                    tvLessorReviewMsg.text = it[0].lessorReview
                    tvSoundInsulationRating.text = getString(Preference.getIdFromString(it[0].soundInsulation))
                    tvPestRating.text = getString(Preference.getIdFromString(it[0].pest))
                    tvSunlightRating.text = getString(Preference.getIdFromString(it[0].lightning))
                    tvWaterpressureRating.text = getString(Preference.getIdFromString(it[0].waterPressure))
                    tvHouseReviewMsg.text = it[0].review
                    tvOverallEvaluationMsg.text = it[0].totalEvaluation
                }
                binding.btnShowAllReviews.setOnClickListener {
                    val action = BuildingDetailFragmentDirections.actionBuildingDetailFragmentToBuildingAllReviewsFragment(
                        buildingSummaryModel
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

        buildingSummaryModel.reviews.let {
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
        binding.tvAddress.text = buildingSummaryModel.shortAddress
    }

    private fun renderPaymentType() {
        buildingSummaryModel.buildingDeals.let {
            if (!it.isNullOrEmpty()) {
                binding.tvDealTypeContent.text = resources.getString(DealType.findDealTypeFromString(it[0].dealType))
            }
        }
    }

    private fun renderBuildingType() {
        binding.tvBuildingTypeContent.text = getString(BuildingType.findBuildingTypeFromString(buildingSummaryModel.buildingType))
    }

    private fun renderOptions() {
        buildingSummaryModel.reviews.let {
            if (!it.isNullOrEmpty()) {
                if (!it[0].furnitures.isNullOrEmpty()) {
                    binding.tvCharacteristicsContent.text = buildingSummaryModel.reviews[0].furnitures.joinToString(separator = ", ") { furniture ->
                        resources.getString(Options.getOptionFromString(furniture))
                    }
                }
            }
        }
    }

    // FIXME: Consider using an adapter..
    private fun renderPictures() {
        buildingSummaryModel.reviews.let {
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