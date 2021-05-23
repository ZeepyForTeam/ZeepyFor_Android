package com.example.zeepyforandroid.community.postingdetail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.example.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostingDetailFragment : BaseFragment<FragmentPostingDetailBinding>() {
    private val viewModel by viewModels<PostingDetailViewModel>()
    private val args: PostingDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changePosting(args.postingModel)
    }

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

        setToolbar()
        setParticipaitonButton()
        setPictureRecyclerview()
        loadPictures()
        setAchievementTextColor()
        setCommentsRecyclerView()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("커뮤니티")
            setRightButton(R.drawable.btn_like){

            }
            setBackButton{

            }
        }
    }

    private fun setParticipaitonButton() {
        binding.btnParticipation.apply {
            setText("공구 참여하기")
            setParticipationButton()
            onClick{

            }
        }
    }

    private fun setPictureRecyclerview() {
        binding.rvPicturePosting.apply {
            adapter = PostingPictureAdapter()
            addItemDecoration(ItemDecoration(0, 8))
        }
    }

    private fun loadPictures() {
        viewModel.posting.observe(viewLifecycleOwner) {
            (binding.rvPicturePosting.adapter as PostingPictureAdapter).submitList(it.picturesPosting)

        }
    }

    private fun setAchievementTextColor() {
        binding.tvRateAchievement.apply {
            val splitIndex = text.indexOf("/")
            val lastIndex = text.lastIndex
            val color = ContextCompat.getColor(requireContext(), R.color.zeepy_gray_9a)

            val spannableText = SpannableStringBuilder().append(
                text.subSequence(0, splitIndex)
            ).color(color) {
                append(text.subSequence(splitIndex, lastIndex+1))
            }
            text = spannableText
        }
    }

    private fun setCommentsRecyclerView() {
        binding.rvComments.apply {
            adapter = CommentsAdapter()
            addItemDecoration(ItemDecoration(8,0))
        }
        (binding.rvComments.adapter as CommentsAdapter).submitList(viewModel.posting.value?.comments)
    }
}