package com.zeepy.zeepyforandroid.community.postingdetail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingDetail
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ParticipationDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.zeepy.zeepyforandroid.mainframe.MainActivity
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragment
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.NetworkStatus
import com.zeepy.zeepyforandroid.util.ext.hideKeyboard
import com.zeepy.zeepyforandroid.util.ext.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class PostingDetailFragment: BaseFragment<FragmentPostingDetailBinding>() {
    private val viewModel by viewModels<PostingDetailViewModel>()
    private val args: PostingDetailFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostingDetailBinding {
        return FragmentPostingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changePostingId(args.postingModel.id)


        setToolbar()
        setPictureRecyclerview()
        getPostingDetailContent()
        changePostingDatas()
        setSwipeRefreshLayout()
        setParticipationButton()
        setCommentsRecyclerView()
        writeComment()
        checkIsParticipated()
        setComments()
        interceptBackPressed()
        modifyPosting()

    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("커뮤니티")
            setScrapButton {
                if(binding.checkboxScrap.isChecked) {
                    viewModel.scrapPosting()
                } else {
                    viewModel.cancelScrapPosting()
                }
            }
            setBackButton{
                findNavController().popBackStack()
            }
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                changePostingDatas()
                this.isRefreshing = false
            }
        }
    }

    private fun setParticipationButton() {
        binding.btnParticipation.apply {
            setText("공구 참여하기")
            setParticipationButton()
            onClick{}
        }
    }

    private fun setParticipationButtonText() {
        viewModel.postingDetail.value?.data?.apply {
            binding.btnParticipation.apply {
                if (isParticipant) {
                    setText("이미 구매에 참여하셨어요!")
               } else {
                   setText("참여하고 돈 아끼기")
                }
            }
        }
    }

    private fun getPostingDetailContent() {
        viewModel.postingId.observe(viewLifecycleOwner) {
            viewModel.fetchPostingDetailContent()
        }
    }

    private fun changePostingDatas() {
        viewModel.postingDetail.observe(viewLifecycleOwner) { postingdetail ->
            Log.e("tag","${postingdetail.data?.typePosting}")

            when(postingdetail.status) {
                NetworkStatus.State.LOADING -> {
                    controlLoadingAnimation(true)
                }
                NetworkStatus.State.SUCCESS -> {
                    (binding.rvPicturePosting.adapter as PostingPictureAdapter).submitList(postingdetail.data?.picturesPosting)
                    viewModel.changeIsGroupPurchase()
                    viewModel.changeCommentList(postingdetail.data?.comments)
                    setParticipationButtonText()
                    checkLikeButton(postingdetail.data?.isLiked ?: false)
                    updateAchievementRate()
                    controlLoadingAnimation(false)
                }
                NetworkStatus.State.ERROR -> {
                    controlLoadingAnimation(false)
                }
            }
        }
    }

    private fun checkIsParticipated() {
        binding.btnParticipation.onClick {
           if (viewModel.postingDetail.value?.data?.isParticipant == false) {
               participatePurchase()
           } else {
               cancelParticipation()
           }
        }

        viewModel.participationComment.observe(viewLifecycleOwner) { comment ->
            if (!comment.isNullOrEmpty()) {
                viewModel.participateGroupPurchase()
            }
        }
    }

    private fun setPictureRecyclerview() {
        binding.rvPicturePosting.apply {
            adapter = PostingPictureAdapter()
            addItemDecoration(ItemDecoration(0, 8))
        }
    }

    private fun updateAchievementRate() {
        viewModel.achievementRate.observe(viewLifecycleOwner) {
            binding.progressbarAchievement.progress = it
        }
        binding.tvRateAchievement.apply {
            viewModel.postingDetail.value?.data?.run {
                val participantsCount = (participants.size).toDouble()
                val target = targetNumberOfPeople?.toDouble()
                if (targetNumberOfPeople != null && this.participants.isNotEmpty()) {
                    viewModel.changeAchievement(((participantsCount/target!!)* PERCENTAGE).toInt())
                } else {
                    viewModel.changeAchievement(0)
                }

                val achievementText = "${this?.participants?.size}명 / ${this?.targetNumberOfPeople}명 "
                val splitIndex = achievementText.indexOf("/")
                val lastIndex = achievementText.lastIndex
                val color = ContextCompat.getColor(requireContext(), R.color.zeepy_gray_9a)

                val spannableText = SpannableStringBuilder().append(
                    achievementText.subSequence(0, splitIndex)
                ).color(color) {
                    append(achievementText.subSequence(splitIndex, lastIndex+1))
                }
                text = spannableText
            }
        }
    }

    private fun setCommentsRecyclerView() {
        binding.rvComments.addItemDecoration(ItemDecoration(8,0))
        viewModel.commentList.observe(viewLifecycleOwner) { comments ->
            binding.rvComments.apply {
                adapter = CommentsAdapter(
                    CommentAuthenticatedModel(
                        viewModel.userId.value ?: -1,
                        viewModel.postingDetail.value?.data?.writerUserIdx,
                        null
                    ), object : CommentsAdapter.WriteNestedCommentListener{
                        override fun write(item: CommentModel) {
                            viewModel.changeSuperCommentId(item.commentId)
                            binding.etComment.requestFocus()
                            requireContext().showKeyboard(binding.etComment)
                        }
                    }
                )
            }
        }
    }

    private fun checkLikeButton(isLiked: Boolean) {
        binding.toolbar.binding.checkboxScrap.apply {
            isChecked = isLiked
        }
    }

    private fun setComments() {
        viewModel.commentList?.observe(viewLifecycleOwner) { comments ->
            (binding.rvComments.adapter as CommentsAdapter).submitList(comments)
            binding.scrollviewCommunity.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    private fun writeComment() {
        binding.btnWriteComment.setOnClickListener {
            viewModel.postCommentToServer()
            binding.etComment.text?.clear()
            requireActivity().hideKeyboard(this.binding.root)
        }
    }

    private fun controlLoadingAnimation(play: Boolean) {
        binding.lottiePostingDetail.run {
            if (play) {
                this.visibility = View.VISIBLE
                this.playAnimation()
            } else {
                this.visibility = View.GONE
                this.cancelAnimation()
            }
        }
    }

    private fun participatePurchase() {
        val participationDialog = ParticipationDialog(object : ParticipationDialog.ParticipationListener{
            override fun participation(participation: String) {
                viewModel.changeParticipationComment(participation)
            }
        })
        participationDialog.show(childFragmentManager, this.tag)
    }

    private fun cancelParticipation() {
        val cancelParticipationDialog = ZeepyDialogBuilder("이미 참여한 공구에요!\n참여를 취소하실건가요 T-T?", "community")
        cancelParticipationDialog.setLeftButton(R.drawable.box_grayf9_8dp,"참여 취소")
            .setRightButton(R.drawable.box_green33_8dp,"참여할게요!:)")
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    viewModel.cancelParticipation()
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }
            })

        cancelParticipationDialog.build().show(childFragmentManager, this.tag)
    }

    private fun interceptBackPressed() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.superCommentId.value != null) {
                    viewModel.changeSuperCommentId(null)

                } else {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun modifyPosting() {
        binding.ivModifyPosting.setOnClickListener {
            val modifyBottomSheet = ModifyBottomSheetDialogFragment(object : ModifyBottomSheetDialogFragment.ModifyPostingListener{
                override fun modify() {
                    moveToModify()
                }

                override fun delete() {
                    viewModel.deletePosting()
                }
            })
            modifyBottomSheet.show(childFragmentManager, this.tag)
        }
        viewModel.isDeletedPosting.observe(viewLifecycleOwner){ isDeleted ->
            if (isDeleted) {
                findNavController().popBackStack()
            }
        }
    }

    private fun moveToModify() {
        when(viewModel.postingDetail.value?.data?.typePosting) {
            "공구" ->
                findNavController().navigate(R.id.action_postingDetailFragment_to_writeGroupPurchaseFragment)
            "나눔", "친구" ->
                findNavController().navigate(R.id.action_postingDetailFragment_to_writeGroupPurchaseFragment)
            else -> throw IllegalArgumentException("error posting type")
        }
    }

    companion object {
        private const val PERCENTAGE = 100
    }
}