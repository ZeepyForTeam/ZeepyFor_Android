package com.example.zeepyforandroid.community.storyzip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.example.zeepyforandroid.databinding.FragmentStoryZipBinding
import com.example.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.example.zeepyforandroid.util.ItemDecoration


class StoryZipFragment : BaseFragment<FragmentStoryZipBinding>() {
    private val viewModel by activityViewModels<CommunityFrameViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoryZipBinding {
        return FragmentStoryZipBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStoryZipRecyclerView()
        viewModel.getPostingList()
        updatePostings()
        initPostingTag()
    }

    private fun setStoryZipRecyclerView() {
        binding.rvStoryzip.apply {
            adapter = StoryZipAdapter{
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToPostingDetailFragment(
                    it
                )
                requireParentFragment().requireParentFragment().requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
            addItemDecoration(ItemDecoration(8,0))
        }
    }

    private fun updatePostings() {
        viewModel.postingList.observe(viewLifecycleOwner){
            (binding.rvStoryzip.adapter as StoryZipAdapter).submitList(it)
        }
    }

    private fun initPostingTag() {
        binding.radiogroupTag.check(binding.rbTagEverything.id)
    }
}