package com.zeepy.zeepyforandroid.community.postingdetail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.zeepy.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
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
        writeComment()
        setComments()

        viewModel.achievementRate.observe(viewLifecycleOwner) {
            binding.progressbarAchievement.progress = it
        }
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
            updateAchievementRate()

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

    private fun updateAchievementRate() {
        binding.tvRateAchievement.apply {
            viewModel.postingDetail.value?.run {
                val participantsCount = (participants.size).toDouble()
                val target = targetNumberOfPeople.toDouble()
                if (targetNumberOfPeople != null && this.participants.isNotEmpty()) {
                    viewModel.changeAchievement(((participantsCount/target)* PERCENTAGE).toInt())
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
            viewModel.postCommentToServer()
            binding.etComment.text?.clear()
            requireActivity().hideKeyboard(this.binding.root)
        }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    companion object {
        private const val PERCENTAGE = 100
    }
}