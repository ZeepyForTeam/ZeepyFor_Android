package com.zeepy.zeepyforandroid.community.storyzip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
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
class ZipFragment : BaseFragment<FragmentZipBinding>(){
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

        initPostingTag()
        setStoryZipRecyclerView()
        updatePostings()
        changeCategory()
        getCategoryPostingList()
        changeAddress()
    }

    override fun onResume() {
        super.onResume()
        initCommunityType()
        getCheckedbutton(binding.radiogroupTag.checkedRadioButtonId)
        (requireActivity() as MainActivity).initialCommunityType = null
    }

    private fun initCommunityType() {
        val category = (requireActivity() as MainActivity).initialCommunityType
        when(category) {
            PostingType.JOINTPURCHASE.name -> {
                binding.rbTabGroupPurchase.isChecked = true
            }
            PostingType.NEIGHBORHOODFRIEND.name -> {
                binding.rbTagFriends.isChecked = true
            }
            PostingType.FREESHARING.name -> {
                binding.rbTagFreeShare.isChecked = true
            }
            else -> {
                binding.rbTagEverything.isChecked = true

            }
        }
    }

    private fun setStoryZipRecyclerView() {
        binding.rvStoryzip.apply {
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

    private fun changeCategory() {
        binding.radiogroupTag.setOnCheckedChangeListener { _, checkedId ->
            getCheckedbutton(checkedId)
        }
    }

    private fun getCheckedbutton(checkedId: Int) {
        when (checkedId) {
            binding.rbTagEverything.id -> {
                viewModel.changeCategory(null)
            }
            binding.rbTabGroupPurchase.id -> {
                viewModel.changeCategory(PostingType.JOINTPURCHASE.name)
            }
            binding.rbTagFreeShare.id -> {
                viewModel.changeCategory(PostingType.FREESHARING.name)
            }
            binding.rbTagFriends.id -> {
                viewModel.changeCategory(PostingType.NEIGHBORHOODFRIEND.name)
            }
        }
    }

    private fun changeAddress() {
        viewModel.selectedAddress.observe(viewLifecycleOwner) {
            viewModel.fetchPostingList()
        }
    }

    private fun getCategoryPostingList() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            if (!viewModel.addressList.value.isNullOrEmpty()) {
                viewModel.fetchPostingList()
            }
        }
    }

    private fun updatePostings() {
        viewModel.postingList.observe(viewLifecycleOwner) { postingList ->
            when (postingList.status) {
                NetworkStatus.State.SUCCESS -> {
                    updatePostingList(postingList.data)
                }
            }
        }
    }

    private fun updatePostingList(updateData: List<PostingListModel>?) {
        val postingListAdapter = (binding.rvStoryzip.adapter as ZipAdapter)
        if (!postingListAdapter.currentList.equals(updateData)) {
            postingListAdapter.run {
                submitList(updateData)
                binding.rvStoryzip.scrollToPosition(0)
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

    private fun initPostingTag() {
        binding.radiogroupTag.check(binding.rbTagEverything.id)
    }
}