package com.zeepy.zeepyforandroid.home

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentHomeBinding
import com.zeepy.zeepyforandroid.databinding.ZeepyDialogBinding
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

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
                if(userPreferenceManager.fetchUserAccessToken().isNullOrEmpty()) {
                    showLoginDialog()
                } else {
                    findNavController().navigate(R.id.action_communityMainFragment_to_communitySelectCategoryFragment)
                }
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

    private fun showLoginDialog() {
        val loginDialog = ZeepyDialogBuilder(resources.getString(R.string.login_notice_message),null)

        loginDialog
            .setLeftButton(R.drawable.box_grayf9_8dp, "삭제")
            .setRightButton(R.drawable.box_blue_59_8dp, "좋았어, 로그인하기!")
            .setButtonHorizontalWeight(0.287f, 0.712f)
            .build()
            .show(childFragmentManager, this@HomeFragment.tag)
    }
}