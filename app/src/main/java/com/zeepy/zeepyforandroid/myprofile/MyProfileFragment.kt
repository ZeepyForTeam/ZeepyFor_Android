package com.zeepy.zeepyforandroid.myprofile

import android.content.Intent
import android.hardware.usb.UsbDevice.getDeviceName
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMyProfileBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyProfileOptionsAdapter
import com.zeepy.zeepyforandroid.myprofile.viewmodel.MyProfileViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>() {
    private val viewModel by viewModels<MyProfileViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding {
        return FragmentMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setButtonsOnClickListener()
        setOptionsRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setMainMsg()
    }

    private fun setButtonsOnClickListener() {
        binding.ivManageAddress.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageAddressFragment)
        }
        binding.ivManageReview.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageReviewFragment)
        }
        binding.ivWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_wishListFragment)
        }
    }

    private fun setOptionsRecyclerView() {
        val options = arrayOf("환경설정", "문의 및 의견 보내기", "지피의 지기들", "현재 버전 1.1")

        binding.rvOptionsList.apply {
            setHasFixedSize(true)
            adapter = MyProfileOptionsAdapter(options) {
                when (it) {
                    0 -> findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
                    1 -> sendEmailToAdmin()
                    2 -> findNavController().navigate(R.id.action_myProfileFragment_to_ziggysFragment)
                }
            }
        }
    }

    private fun setMainMsg() {
        if (userPreferenceManager.fetchIsAlreadyLogin()) {
            viewModel.getUserNicknameAndEmail(userPreferenceManager.fetchUserEmail())
            val nickname = userPreferenceManager.fetchUserNickname()
            if (nickname != "") {
                changePartialText(nickname, true)
            } else {
                // try to fetch nickname?
            }
        } else {
            changePartialText("", false)
        }
    }

    /**
     * Changes part of the text's font type and color using annotations in strings.xml
     * @see android.text.Annotation
     */
    private fun changePartialText(nickname: String, loggedIn: Boolean) {
        val text = if (loggedIn) {
            getText(R.string.myprofile_loggedin) as SpannedString
        } else {
            getText(R.string.myprofile_loggedout) as SpannedString
        }
        val spannable = SpannableStringBuilder(text)

        fun SpannableStringBuilder.applyArgAnnotations(vararg args: Any) {
            val annotations = text.getSpans(0, text.length, android.text.Annotation::class.java)
            annotations.forEach { annotation ->
                when (annotation.key) {
                    "args" -> {
                        val argIndex = Integer.parseInt(annotation.value)
                        when (val arg = args[argIndex]) {
                            is String -> {
                                spannable.replace(
                                    spannable.getSpanStart(annotation),
                                    spannable.getSpanEnd(annotation),
                                    arg
                                )
                            }
                        }
                    }
                }
            }
        }

        fun SpannableStringBuilder.applyOtherAnnotations() {
            val annotations = text.getSpans(0, text.length, android.text.Annotation::class.java)
            annotations.forEach { annotation ->
                when (annotation.key) {
                    "clickTo" -> {
                        spannable.setSpan(
                            object : ClickableSpan() {
                                val dest = annotation.value
                                override fun onClick(widget: View) {
                                    when (dest) {
                                        "signIn" -> {
                                            //Log.e("parent of onClick 로그인", parentFragment.toString())
                                            //Log.e("navcontroller.currentBackStackEntry", "" + findNavController().currentBackStackEntry)
                                            //Log.e("navcontroller.previousBackStackEntry", "" + findNavController().previousBackStackEntry)
                                            requireActivity().findNavController(R.id.nav_host_fragment)
                                                .navigate(R.id.action_mainFrameFragment_to_signInFragment)
                                        }
                                        "editProfile" -> {
                                            findNavController().navigate(R.id.action_myProfileFragment_to_EditMyProfileFragment)
                                        }
                                        else -> {
                                            throw IllegalArgumentException("Invalid destination argument was given")
                                        }
                                    }
                                }
                            },
                            spannable.getSpanStart(annotation),
                            spannable.getSpanEnd(annotation),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    "font" -> {
                        val fontName = annotation.value
                        val typeface = ResourcesCompat.getFont(
                            requireContext(),
                            context?.resources!!.getIdentifier(
                                fontName,
                                "font",
                                context?.packageName
                            )
                        )
                        spannable.setSpan(
                            CustomTypefaceSpan(typeface!!),
                            spannable.getSpanStart(annotation),
                            spannable.getSpanEnd(annotation),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }

        if (loggedIn) {
            spannable.applyArgAnnotations(nickname)
        }
        spannable.applyOtherAnnotations()

        // For some reason, having multiple spans with setting ForegroundColorSpan caused incorrect color to be used.
        // Hence, this part was deliberately separated from the above Kotlin Extension functions.
        val annotations = text.getSpans(0, text.length, android.text.Annotation::class.java)
        annotations.forEach { annotation ->
            if (annotation.key == "color") {
                val colorName = annotation.value
                val colorId = context?.resources?.getIdentifier(
                    colorName,
                    "color",
                    context?.packageName
                )
                spannable.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            colorId!!
                        )
                    ),
                    spannable.getSpanStart(annotation),
                    spannable.getSpanEnd(annotation),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        binding.tvMainMsg.text = spannable
        binding.tvMainMsg.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun sendEmailToAdmin() {
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_SUBJECT, "")
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf("zeepy.official@gmail.com"))
        email.putExtra(
            Intent.EXTRA_TEXT,
            String.format(
                "App Version : %s\nDevice : %s\nAndroid(SDK) : %d(%s)\n내용 : ",
                BuildConfig.VERSION_NAME,
                Build.MODEL,
                Build.VERSION.SDK_INT,
                Build.VERSION.RELEASE
            )
        )
        email.type = "plain/text"
        startActivity(email)
    }
}