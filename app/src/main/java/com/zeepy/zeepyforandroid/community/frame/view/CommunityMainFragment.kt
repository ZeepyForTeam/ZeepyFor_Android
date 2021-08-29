package com.zeepy.zeepyforandroid.community.frame.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityMainFragment : BaseFragment<FragmentCommunityMainBinding>() {
    private val viewModel by viewModels<CommunityFrameViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityMainBinding {
        return FragmentCommunityMainBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressListFromServer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        initViewPager()
        changeZipFragment()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setCommunityLocation()
            setTitle(viewModel.selectedAddress.value ?: "주소 등록하기")
            viewModel.selectedAddress.observe(viewLifecycleOwner) {
                setTitle(it ?: "주소 등록하기")
            }

            setRightButton(R.drawable.ic_btn_write) {
                requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_communitySelectCategoryFragment)
            }
            setRightButtonMargin(32)

            binding.textviewToolbar.setOnClickListener {
                changeAddress()
            }
        }
    }

    private fun changeAddress() {
        val isAlreadyLogin = userPreferenceManager.fetchIsAlreadyLogin()
        val isAlreadyRegisterAddress = !viewModel.addressList.value.isNullOrEmpty()

        when {
            !isAlreadyLogin -> {
                showLoginDialog()
            }

            isAlreadyLogin && isAlreadyRegisterAddress -> {
                val addressList = viewModel.addressList.value!!.toTypedArray()
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToChangeAddressFragment(addressList)
                action.isCommunityTheme = true
                requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }

            isAlreadyLogin && !isAlreadyRegisterAddress -> {
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToReviewFrameFragment()
                requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
        }
    }

    private fun initViewPager() {
        binding.viewpagerCommunity.adapter = CommunityFrameAdapter(this@CommunityMainFragment)
        TabLayoutMediator(binding.tablayout, binding.viewpagerCommunity) {tab, pos ->
            tab.text = when(pos) {
                0 -> TAB_NAME[0]
                1 -> TAB_NAME[1]
                else -> throw RuntimeException("TabLayout Error")
            }
        }.attach()
    }

    private fun changeZipFragment() {
        binding.viewpagerCommunity.run {
            viewModel.changeCurrentFragmentId(this.currentItem)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.changeCurrentFragmentId(position)
                }
            })
        }
    }

    private fun showLoginDialog() {
        val loginDialog = ZeepyDialogBuilder(resources.getString(R.string.login_notice_message), null)

        loginDialog.setLeftButton(R.drawable.box_grayf9_8dp, "취소")
            .setRightButton(R.drawable.box_green33_8dp, "좋았어, 로그인하기!")
            .setButtonHorizontalWeight(0.287f, 0.712f)
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }
                override fun clickRightButton(dialog: ZeepyDialog) {
                    findNavController().navigate(R.id.action_mainFrameFragment_to_signInFragment)
                    dialog.dismiss()
                }
            })
            .build()
            .show(childFragmentManager, this@CommunityMainFragment.tag)
    }

    companion object {
        private val TAB_NAME = listOf("이야기ZIP", "나의ZIP")
    }
}