package com.zeepy.zeepyforandroid.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.databinding.FragmentHomeBinding
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        Log.e("access token", "${userPreferenceManager.fetchUserAccessToken()}")

        writeReview()
        setFilterList()
        changeAddress()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAddressListFromServer()
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setCommunityLocation()
            viewModel.addressList.observe(viewLifecycleOwner) { addresses ->
                val selectedAddress = addresses.find { it.isAddressCheck }
                if (addresses.isNullOrEmpty()) {
                    setTitle("주소 등록하기")
                } else {
                    selectedAddress?.let { setTitle(it.cityDistinct) }
                }
            }
        }
    }

    private fun changeAddress() {
        binding.toolbar.binding.textviewToolbar.setOnClickListener {
            if (userPreferenceManager.fetchIsAlreadyLogin()) {
                if (viewModel.addressList.value.isNullOrEmpty()) {
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToReviewFrameFragment()
                    action.isJustRegisterAddress = true
                    findNavController().navigate(action)
                } else {
                    val addresses = viewModel.addressList.value!!.toTypedArray()
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToChangeAddressFragment(
                            addresses
                        )
                    requireParentFragment().requireParentFragment().findNavController()
                        .navigate(action)
                }
            } else {
                showLoginDialog()
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
            if (userPreferenceManager.fetchIsAlreadyLogin()) {
                val action =
                    MainFrameFragmentDirections.actionMainFrameFragmentToReviewFrameFragment()
                action.isJustRegisterAddress = false
                findNavController().navigate(action)
            } else {
                showLoginDialog()
            }
        }
    }

    private fun showLoginDialog() {
        val loginDialog =
            ZeepyDialogBuilder(resources.getString(R.string.login_notice_message), null)

        loginDialog.setLeftButton(R.drawable.box_grayf9_8dp, "삭제")
            .setRightButton(R.drawable.box_blue_59_8dp, "좋았어, 로그인하기!")
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
            .show(childFragmentManager, this@HomeFragment.tag)
    }
}