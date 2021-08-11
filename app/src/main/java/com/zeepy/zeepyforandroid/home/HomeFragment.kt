package com.zeepy.zeepyforandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentHomeBinding
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        writeReview()
        setFilterList()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("을지로 3가")
            setCommunityLocation()
            setRightButton(R.drawable.ic_btn_write) {
                findNavController().navigate(R.id.action_communityMainFragment_to_communitySelectCategoryFragment)
            }
            setRightButtonMargin(32)


            binding.textviewToolbar.setOnClickListener {
                requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_changeAddressFragment)
            }
        }
    }

    private fun setFilterList() {
        binding.recyclerviewFilter.run {
            adapter = HomeFilterAdapter()
            (adapter as HomeFilterAdapter).notifyDataSetChanged()
            addItemDecoration(ItemDecoration(0, 8))
        }
    }

    private fun writeReview() {
        binding.buttonWriteReview.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFrameFragment_to_reviewFrameFragment)
        }
    }
}