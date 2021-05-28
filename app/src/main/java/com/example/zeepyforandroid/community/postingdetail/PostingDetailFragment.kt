package com.example.zeepyforandroid.community.postingdetail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.example.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.SharedUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//Todo: 댓글을 입력하면 RecyclerView에 데이터 추가하고 뷰 업데이트
@AndroidEntryPoint
class PostingDetailFragment: BaseFragment<FragmentPostingDetailBinding>() {
    @Inject lateinit var prefs: SharedUtil
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

        Log.e("userIdx", prefs.getSharedPrefs("userIdx", -1).toString())

        setToolbar()
        setSwipeRefreshLayout()
        setParticipationButton()
        setPictureRecyclerview()
        setCommentsRecyclerView()
        setAchievementTextColor()
        changePostingDatas()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("커뮤니티")
            setScrapButton {}
            setBackButton{
                findNavController().popBackStack()
            }
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                changePostingDatas()
                setComments()
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

    private fun changePostingDatas() {
        viewModel.posting.observe(viewLifecycleOwner) {
            (binding.rvPicturePosting.adapter as PostingPictureAdapter).submitList(it.picturesPosting)
            viewModel.changeIsGroupPurchase()
            if(it.isSetAchievement) {
                binding.layoutAchievement.background = null
            } else {
                binding.layoutAchievement.setBackgroundResource(R.drawable.box_grayf4_8dp)
            }
        }
    }

    private fun setPictureRecyclerview() {
        binding.rvPicturePosting.apply {
            adapter = PostingPictureAdapter()
            addItemDecoration(ItemDecoration(0, 8))
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
        viewModel.posting.observe(viewLifecycleOwner) { posting ->
            binding.rvComments.apply {
                adapter = CommentsAdapter(
                    CommentAuthenticatedModel(
                        prefs.getSharedPrefs("userIdx", -1),
                        posting.writerUserIdx,
                        null
                    )
                )
                setComments()
                addItemDecoration(ItemDecoration(8,0))
            }
        }
    }
    private fun setComments() {
        //Swipe Refresh 시 ItemDecoration 중복 추가 방지를 위해 위해 따로 분리
        viewModel.posting.observe(viewLifecycleOwner) { posting ->
            (binding.rvComments.adapter as CommentsAdapter).submitList(posting.comments)
        }
    }
}