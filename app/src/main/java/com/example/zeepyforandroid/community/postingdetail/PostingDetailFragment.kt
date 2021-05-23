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
import androidx.navigation.fragment.navArgs
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentPostingDetailBinding
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.SharedUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        prefs.putSharedPref("userId", 11)

        setToolbar()
        setSwipeRefreshLayout()
        setParticipaitonButton()
        setPictureRecyclerview()
        setCommentsRecyclerView()
        setAchievementTextColor()
        changeAchievementVisibility()
        setComments()
        loadPictures()
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

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                loadPictures()
                setComments()
                this.isRefreshing = false
            }
        }
    }

    private fun setParticipaitonButton() {
        binding.btnParticipation.apply {
            setText("공구 참여하기")
            setParticipationButton()
            onClick{
                Log.e("name", prefs.getSharedPrefs("userId", -1).toString())
            }
        }
    }

    private fun changeAchievementVisibility() {
        viewModel.posting.observe(viewLifecycleOwner) {
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
    }

    private fun setComments() {
        (binding.rvComments.adapter as CommentsAdapter).submitList(viewModel.posting.value?.comments)

    }
}