package com.zeepy.zeepyforandroid.community.storyzip

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.frame.view.CommunityFrameFragment
import com.zeepy.zeepyforandroid.community.frame.view.CommunityMainFragment
import com.zeepy.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialog.Companion.COMMUNITY_IS_COMPLETED
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentZipBinding
import com.zeepy.zeepyforandroid.enum.PostingType
import com.zeepy.zeepyforandroid.home.DirectTransitionListener
import com.zeepy.zeepyforandroid.mainframe.MainActivity
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.util.ItemDecoration
import com.zeepy.zeepyforandroid.util.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZipFragment : BaseFragment<FragmentZipBinding>() {
    private val viewModel by viewModels<CommunityFrameViewModel>(ownerProducer = { requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentZipBinding {
        return FragmentZipBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setStoryZipRecyclerView()
        updatePostings()
        changeAddress()
        swipeRefreshPostingList()
        checkFilter()
        changeZIP()
//        changeFilterPosting()
        fetchPaginationPostings()
        fetchPostingDirectFromHome((requireActivity() as MainActivity).initialCommunityType)
    }

    private fun swipeRefreshPostingList() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                resetPostingList()
                viewModel.fetchPostingList()
            }
            viewModel.postingList.observe(viewLifecycleOwner) {
                isRefreshing = false
            }
        }
    }

    private fun setStoryZipRecyclerView() {
        binding.rvStoryzip.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ZipAdapter { posting ->
                if (posting.isCompleted) {
                    showIsCompletedDialog(posting)
                } else {
                    goToPostingDetailFragment(posting)
                }
            }
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun changeAddress() {
        viewModel.selectedAddress.observe(viewLifecycleOwner) {
            Log.e("4444","44444")
            resetPostingList()
            viewModel.fetchPostingList()
        }
    }

    private fun checkFilter() {
        binding.radiogroupTag.setOnCheckedChangeListener { radiogroup, isChecked ->
            Log.e("3333","3333")
            resetPostingList()
            changeFilter(binding.radiogroupTag)
            viewModel.fetchPostingList()
            binding.rvStoryzip.scrollToPosition(0)
        }
    }

    private fun changeFilter(radiogroup: RadioGroup) {
        when (radiogroup.checkedRadioButtonId) {
            binding.rbTagEverything.id -> {
                (requireActivity() as MainActivity).initialCommunityType = null
                viewModel.changeSelectedFilter(null)
            }
            binding.rbTabGroupPurchase.id -> {
                PostingType.JOINTPURCHASE.name.run {
                    viewModel.changeSelectedFilter(this)
                    (requireActivity() as MainActivity).initialCommunityType = this
                }
            }
            binding.rbTagFreeShare.id -> {
                PostingType.FREESHARING.name.run {
                    viewModel.changeSelectedFilter(this)
                    (requireActivity() as MainActivity).initialCommunityType = this
                }
            }
            binding.rbTagFriends.id -> {
                PostingType.NEIGHBORHOODFRIEND.name.run {
                    viewModel.changeSelectedFilter(this)
                    (requireActivity() as MainActivity).initialCommunityType = this
                }
            }
        }
    }

//    private fun changeFilterPosting() {
//        viewModel.selectedFilter.observe(viewLifecycleOwner) {
//            Log.e("2222","2222")
//            resetPostingList()
//            viewModel.fetchPostingList()
//        }
//    }

    private fun changeZIP() {
        viewModel.currentFragmentId.observe(viewLifecycleOwner) {
            Log.e("1111","11111")
            resetPostingList()
            changeFilter(binding.radiogroupTag)
        }
    }

    private fun updatePostings() {
        viewModel.postingList.observe(viewLifecycleOwner) { postingList ->
            updatePostingList(postingList)
        }
    }

    private fun updatePostingList(updateData: List<PostingListModel>) {
        val postingListAdapter = (binding.rvStoryzip.adapter as ZipAdapter)
        if (postingListAdapter.currentList != updateData) {
            (binding.rvStoryzip.adapter as ZipAdapter).submitList(updateData)
        }
    }

    private fun fetchPaginationPostings() {
        binding.rvStoryzip.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisible >= layoutManager.itemCount - 5) {
                    Log.e("id", "${viewModel.currentFragmentId.value}")
                    if(viewModel.currentFragmentId.value != 1 &&
                        viewModel.paginationIdx.value != -1) {
                        viewModel.fetchPostingList()
                    }
                }
            }
        })
    }

    private fun resetPostingList() {
        viewModel.removePostingList()
        viewModel.changePaginationIdx(0)
    }

    private fun fetchPostingDirectFromHome(type: String?) {
        with(binding) {
            when (type) {
                PostingType.FREESHARING.name -> rbTagFreeShare.isChecked = true
                PostingType.JOINTPURCHASE.name -> rbTabGroupPurchase.isChecked = true
                PostingType.NEIGHBORHOODFRIEND.name -> rbTagFriends.isChecked = true
                else -> rbTagEverything.isChecked = true
            }
        }
    }

    private fun showIsCompletedDialog(posting: PostingListModel) {
        val isCompletedDialog = ZeepyDialogBuilder("모집이 완료된 글이에요!", COMMUNITY_IS_COMPLETED)
            .setButtonHorizontalWeight(0.7f, 0.3f)
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    goToPostingDetailFragment(posting)
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }
            })
            .setLeftButton(R.drawable.box_green33_8dp, "계속 보기")
            .setRightButton(R.drawable.box_grayf9_8dp, "나가기")
            .build()

        isCompletedDialog.show(childFragmentManager, this.tag)
    }

    private fun goToPostingDetailFragment(posting: PostingListModel) {
        val action =
            MainFrameFragmentDirections.actionMainFrameFragmentToPostingDetailFragment(posting)
        requireParentFragment().requireParentFragment().requireParentFragment()
            .requireParentFragment().findNavController().navigate(action)
    }
}