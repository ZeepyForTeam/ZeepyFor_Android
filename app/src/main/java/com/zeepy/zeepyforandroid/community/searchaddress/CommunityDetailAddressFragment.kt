package com.zeepy.zeepyforandroid.community.searchaddress

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentCommunityDetailAddressBinding
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan


class CommunityDetailAddressFragment : BaseFragment<FragmentCommunityDetailAddressBinding>() {
    private val viewModel by activityViewModels<CommunityFrameViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityDetailAddressBinding {
        return FragmentCommunityDetailAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutCommunityDetailAddress.tvAddress.text = viewModel.searchAddressQuery.value
        setCommunityTheme()
    }

    private fun setCommunityTheme() {
        val typeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(),R.font.nanum_square_round_extrabold),
            Typeface.NORMAL)
        val spannableString = binding.tvNotice.text.toSpannable()
        spannableString.setSpan(CustomTypefaceSpan(typeface), 0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.apply {
            toolbar.apply {
                setTitle("커뮤니티")
                setBackButton{
                    findNavController().popBackStack()
                }
            }
            layoutCommunityDetailAddress.btnNext.apply {
                setText("다음으로")
                setCommunityUsableButton()
            }
            tvNotice.text = spannableString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
