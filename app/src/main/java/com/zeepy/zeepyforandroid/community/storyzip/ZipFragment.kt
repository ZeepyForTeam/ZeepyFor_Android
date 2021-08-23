package com.zeepy.zeepyforandroid.community.storyzip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentZipBinding
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.util.ItemDecoration
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
        getCheckedbutton(binding.radiogroupTag.checkedRadioButtonId)
    }

    private fun setStoryZipRecyclerView() {
        binding.rvStoryzip.apply {
            adapter = ZipAdapter{
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToPostingDetailFragment(it)
                requireParentFragment().requireParentFragment().requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
            addItemDecoration(ItemDecoration(8,0))
        }
    }

    private fun changeCategory() {
        binding.radiogroupTag.setOnCheckedChangeListener { _, checkedId ->
            getCheckedbutton(checkedId)
        }
    }

    private fun getCheckedbutton(checkedId: Int) {
        when(checkedId) {
            binding.rbTagEverything.id -> {
                viewModel.changeCategory(null)
            }
            binding.rbTabGroupPurchase.id -> {
                viewModel.changeCategory("JOINTPURCHASE")
            }
            binding.rbTagFreeShare.id -> {
                viewModel.changeCategory("FREESHARING")
            }
            binding.rbTagFriends.id -> {
                viewModel.changeCategory("NEIGHBORHOODFRIEND")
            }
        }
    }

    private fun changeAddress() {
        viewModel.addressList.observe(viewLifecycleOwner) {
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
        viewModel.postingList.observe(viewLifecycleOwner){
            (binding.rvStoryzip.adapter as ZipAdapter).submitList(it)
        }
    }

    private fun initPostingTag() {
        binding.radiogroupTag.check(binding.rbTagEverything.id)
    }
}