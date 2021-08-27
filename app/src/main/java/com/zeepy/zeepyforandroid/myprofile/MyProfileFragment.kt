package com.zeepy.zeepyforandroid.myprofile

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
import androidx.core.text.color
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMyProfileBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyProfileOptionsAdapter
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>() {

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding {
        return FragmentMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsOnClickListener()
        setOptionsRecyclerView()
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
        val options = arrayOf("환경설정", "문의 및 의견 보내기", "신고하기", "지피의 지기들", "현재 버전 1.1")

        binding.rvOptionsList.apply {
            setHasFixedSize(true)
            adapter = MyProfileOptionsAdapter(options)
        }
    }

    private fun setMainMsg() {
        if (userPreferenceManager.fetchIsAlreadyLogin()) {
            changePartialText(true)
        } else {
            changePartialText(false)
        }
    }

    /**
     * Changes part of the text's font type and color using annotations in strings.xml
     * @see android.text.Annotation
     */
    private fun changePartialText(loggedIn: Boolean) {
        // FIXME: get stored nickname of the current user from SharedPrefs (When the getUserNicknameAndEmail API will be called? at Login and keep the nickname in SharedPrefs?)
        val nickname = "도로로로로롱"
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
                    "clickable" -> {
                        spannable.setSpan(object: ClickableSpan() {
                            val dest = annotation.value
                            override fun onClick(widget: View) {
                                when (dest) {
                                    "signIn" -> {
                                        findNavController().navigate(R.id.action_mainFrameFragment_to_signInFragment)
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
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    "font" -> {
                        val fontName = annotation.value
                        val typeface = ResourcesCompat.getFont(requireContext(), context?.resources!!.getIdentifier(fontName, "font", context?.packageName))
                        spannable.setSpan(CustomTypefaceSpan(typeface!!), spannable.getSpanStart(annotation), spannable.getSpanEnd(annotation), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    "color" -> {
                        val colorName = annotation.value
                        Log.e("colorname", "" + colorName)
                        val colorId = context?.resources?.getIdentifier(colorName, "color", context?.packageName)
                        Log.e("colorid", "" + colorId)
                        Log.e("zeepyblack3b id", R.color.zeepy_black_3b.toString())
                        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), colorId!!)), spannable.getSpanStart(annotation), spannable.getSpanEnd(annotation), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }

        spannable.applyArgAnnotations(nickname)
        binding.tvMainMsg.text = spannable
        binding.tvMainMsg.movementMethod = LinkMovementMethod.getInstance()
    }
}