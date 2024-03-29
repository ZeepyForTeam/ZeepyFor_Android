package com.zeepy.zeepyforandroid.myprofile

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.DialogClickListener
import com.zeepy.zeepyforandroid.customview.ZeepyDialog
import com.zeepy.zeepyforandroid.customview.ZeepyDialog.Companion.MY_PROFILE
import com.zeepy.zeepyforandroid.customview.ZeepyDialogBuilder
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentEditMyProfileBinding
import com.zeepy.zeepyforandroid.home.DirectTransitionListener
import com.zeepy.zeepyforandroid.home.HomeFragment
import com.zeepy.zeepyforandroid.myprofile.data.ModifyPasswordReqDTO
import com.zeepy.zeepyforandroid.myprofile.viewmodel.MyProfileViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.signin.SignInViewModel
import com.zeepy.zeepyforandroid.signin.SignInViewModel.Companion.KAKAO
import com.zeepy.zeepyforandroid.signin.SignInViewModel.Companion.NAVER
import com.zeepy.zeepyforandroid.signin.SignInViewModel.Companion.ZEEPY
import com.zeepy.zeepyforandroid.util.MetricsConverter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ClassCastException
import javax.inject.Inject

@AndroidEntryPoint
class EditMyProfileFragment : BaseFragment<FragmentEditMyProfileBinding>() {
    private val viewModel by viewModels<MyProfileViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private var frameFragmentListener: DirectTransitionListener? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditMyProfileBinding {
        return FragmentEditMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        onAttachToParentFragment(parentFragment?.parentFragment?.parentFragment, HomeFragment())

        Log.e("token: ", userPreferenceManager.fetchUserAccessToken());

        setToolbar()
        setOnBackPressed()
        setOnClickListeners()

        binding.tvNicknameContent.text = userPreferenceManager.fetchUserNickname()
        binding.tvEmailContent.text = userPreferenceManager.fetchUserEmail()

        when (getUserLoginType()) {
            KAKAO -> {
                val layoutParams = binding.tvEmailContent.layoutParams as ViewGroup.MarginLayoutParams
                binding.ivSnsLogo.load(R.drawable.kakaologo)
                layoutParams.marginStart = MetricsConverter.dpToPixel(8.0F, context).toInt()
                binding.tvEmailContent.layoutParams = layoutParams
            }
            NAVER -> {
                binding.ivSnsLogo.load(R.drawable.naver_logo_s)
            }
            else -> {}
        }

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
                    saveUserLoginType("")
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
                frameFragmentListener?.comeBackHome()

            }
        })
    }

    private fun onAttachToParentFragment(mainframe: Fragment?, frag: Fragment?) {
        try {
            frameFragmentListener = mainframe as DirectTransitionListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("내 정보 수정")
    }

    private fun setOnClickListeners() {
        binding.btnSubmitChange.setOnClickListener {
            if (checkNewPassword()) {
                changePasswordShowConfirmDialog(binding.etPassword.text.toString())
            } else {
                Toast.makeText(context, R.string.input_password_signup, Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLogout.setOnClickListener {
            Log.e("email fetched for logout", userPreferenceManager.fetchUserEmail())
            viewModel.submitLogout()
        }

        binding.tvWithdraw.setOnClickListener {
            viewModel.submitWithdrawal()
        }
    }

    private fun changePasswordShowConfirmDialog(newPassword: String) {
        val confirmDialog =
            ZeepyDialogBuilder(resources.getString(R.string.change_password_confirm), MY_PROFILE)

        confirmDialog.setLeftButton(R.drawable.box_grayf9_8dp, "취소")
            .setRightButton(R.drawable.box_yellowee_8dp, "비밀번호 변경")
            .setButtonHorizontalWeight(0.287f, 0.712f)
            .setDialogClickListener(object : DialogClickListener {
                override fun clickLeftButton(dialog: ZeepyDialog) {
                    dialog.dismiss()
                }

                override fun clickRightButton(dialog: ZeepyDialog) {
                    Toast.makeText(context, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.changeUserPassword(ModifyPasswordReqDTO(newPassword))
                    dialog.dismiss()
                }
            })
            .build()
            .show(childFragmentManager, this.tag)
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun checkNewPassword(): Boolean {
        return if (binding.etPassword.text != null) binding.etPassword.text!!.length in 8..16 else false
    }

    private fun getUserLoginType(): String {
        return userPreferenceManager.fetchUserLoginType()
    }
}