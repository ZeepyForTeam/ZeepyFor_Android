package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentEditMyProfileBinding
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditMyProfileFragment : BaseFragment<FragmentEditMyProfileBinding>() {
    private val viewModel by viewModels<MyProfileViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditMyProfileBinding {
        return FragmentEditMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setOnBackPressed()
        setOnClickListeners()
        Log.e("access token", "${userPreferenceManager.fetchUserAccessToken()}")
        Log.e("parent of EditMyProfileFragment", parentFragment.toString())
        Log.e("parent of parent of EditMyProfileFragment", parentFragment?.parentFragment.toString())
        Log.e("parent of parent of parent of EditMyProfileFragment", parentFragment?.parentFragment?.parentFragment.toString())
        // Observers
        viewModel.isWithdrawn.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(context, "회원 탈퇴하였습니다. 감사합니다.", Toast.LENGTH_SHORT).show()

                userPreferenceManager.apply {
                    saveUserId(-1)
                    saveUserNickname("")
                    saveUserEmail("")
                    saveUserAccessToken("")
                    saveUserRefreshToken("")
                    saveIsAlreadyLogin(false)
                }

                findNavController().popBackStack()
            }
        })
        viewModel.isLoggedOut.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(context, "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show()

                userPreferenceManager.apply {
                    saveUserAccessToken("")
                    saveUserRefreshToken("")
                    saveIsAlreadyLogin(false)
                }

                findNavController().popBackStack()
            }
        })
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("내 정보 수정")
    }

    private fun setOnClickListeners() {
        binding.btnSubmitChange.setOnClickListener {
            changePasswordShowConfirmDialog(this)
        }

        binding.tvLogout.setOnClickListener {
            Log.e("email fetched for logout", userPreferenceManager.fetchUserEmail())
            viewModel.submitLogout()
        }

        binding.tvWithdraw.setOnClickListener {
            viewModel.submitWithdrawal()
        }
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}