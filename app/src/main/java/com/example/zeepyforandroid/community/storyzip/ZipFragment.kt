package com.example.zeepyforandroid.community.storyzip

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.example.zeepyforandroid.databinding.FragmentZipBinding
import com.example.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.example.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZipFragment : BaseFragment<FragmentZipBinding>() {
    private val viewModel by viewModels<CommunityFrameViewModel>(ownerProducer = { requireParentFragment().requireParentFragment().requireParentFragment() })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentZipBinding {
        return FragmentZipBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()

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
            adapter = ZipAdapter{
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
            (binding.rvStoryzip.adapter as ZipAdapter).submitList(it)
        }
    }

    private fun initPostingTag() {
        binding.radiogroupTag.check(binding.rbTagEverything.id)
    }
}