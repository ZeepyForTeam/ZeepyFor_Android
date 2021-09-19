package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentWriteHouseInfoBinding
import com.zeepy.zeepyforandroid.enum.TotalEvaluation.Companion.findTotalEvaluation
import com.zeepy.zeepyforandroid.review.AgeSelectListener
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel

class WriteHouseInfoFragment : BaseFragment<FragmentWriteHouseInfoBinding>() {
    private val viewModel by viewModels<WriteReviewViewModel>(ownerProducer = { requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteHouseInfoBinding {
        return FragmentWriteHouseInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setNextButton()
        selectTotalEvaluation()
        enableNextButton()
        checkIsPostSuccess()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("등록하기")
            setUnUsableButton()
            setOnClickListener {
                showUploadReviewDialog()
            }
        }
    }
    private fun checkIsPostSuccess() {
        viewModel.isPostSuccess.observe(viewLifecycleOwner) {
            requireParentFragment().requireParentFragment().findNavController().popBackStack()
        }
    }

    private fun showUploadReviewDialog() {
        val registerReviewDialog = ZeepyDialogBuilder( "리뷰를 등록하시겠습니까?",null)
            .setContent("*리뷰 등록 후에는 수정하거나 삭제하실 수\n없으니 글 작성에 유의해주세요.\n\n*허위/중복/성의없는 정보 또는 비방글을\n작성할 경우, 서비스 이용이 제한될 수 있습니다.")
            .setLeftButton(R.drawable.box_grayf9_8dp,"취소")
            .setRightButton(R.drawable.box_blue_59_8dp,"확인")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    viewModel.postReviewToServer()
                    dialog.dismiss()
                }
            }).build()
        registerReviewDialog.show(childFragmentManager, this.tag)
    }

    private fun selectTotalEvaluation() {
        binding.groupFinalReview.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.btnGood.id -> viewModel.changeHouseTotalEvaluation(findTotalEvaluation(R.string.total_good))
                binding.btnRecommendation.id -> viewModel.changeHouseTotalEvaluation(
                    findTotalEvaluation(R.string.total_recommendation)
                )
                binding.btnNoRecommendation.id -> viewModel.changeHouseTotalEvaluation(
                    findTotalEvaluation(R.string.total_no_recommendation)
                )
            }
        }
    }

    private fun enableNextButton() {
        viewModel.reviewOfHouse.observe(viewLifecycleOwner) {
            checkInputALL()
        }
        viewModel.houseTotalEvaluation.observe(viewLifecycleOwner) {
            checkInputALL()
        }
    }

    private fun checkInputALL() {
        if (viewModel.checkTotalHouseReviewEmpty()) {
            binding.btnNext.setUnUsableButton()
        } else {
            binding.btnNext.setUsableButton()
        }
    }
}