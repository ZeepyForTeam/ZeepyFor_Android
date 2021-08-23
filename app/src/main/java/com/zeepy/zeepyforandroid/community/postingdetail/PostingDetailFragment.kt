package com.zeepy.zeepyforandroid.community.postingdetail

import android.graphics.Bitmap
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.util.FileConverter
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PostingDetailFragment: BaseFragment<FragmentPostingDetailBinding>() {
    @Inject lateinit var prefs: SharedPreferencesManager
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

        Log.e("userIdx", prefs.getSharedPrefs("userIdx", -1).toString())

        setToolbar()
        setPictureRecyclerview()
        getPostingDetailContent()
        changePostingDatas()
        setSwipeRefreshLayout()
        setParticipationButton()
        setCommentsRecyclerView()
        setAchievementTextColor()
        writeComment()
        setComments()
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

    private fun getPostingDetailContent() {
        viewModel.postingId.observe(viewLifecycleOwner) {
            viewModel.fetchPostingDetailContent()
        }
    }

    private fun changePostingDatas() {
        viewModel.postingDetail.observe(viewLifecycleOwner) { postingdetail ->
            if(postingdetail.picturesPosting.isNullOrEmpty()) {
                binding.rvPicturePosting.visibility = View.GONE
            }
            (binding.rvPicturePosting.adapter as PostingPictureAdapter).submitList(postingdetail.picturesPosting)
            viewModel.changeIsGroupPurchase()
            viewModel.changeCommentList(postingdetail.comments)

//            Observable.just( postingdetail.picturesPosting.map { PictureModel(FileConverter.convertUrlToBitmap(it.picture)) })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                        (binding.rvPicturePosting.adapter as PostingPictureAdapter).submitList(it)
//                        viewModel.changeIsGroupPurchase()
//                        viewModel.changeCommentList(postingdetail.comments)
//                    }, {
//                        it.printStackTrace()
//                    })



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
        binding.rvComments.addItemDecoration(ItemDecoration(8,0))
        viewModel.commentList?.observe(viewLifecycleOwner) { comments ->
            binding.rvComments.apply {
                adapter = CommentsAdapter(
                    CommentAuthenticatedModel(
                        viewModel.userId.value ?: -1,
                        viewModel.postingDetail.value?.writerUserIdx,
                        null
                    )
                )
            }
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
            viewModel.postComment()
            binding.etComment.text?.clear()
        }
    }
}