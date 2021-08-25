package com.zeepy.zeepyforandroid.community

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentCommunitySelectCategoryBinding
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan

class CommunitySelectCategoryFragment: BaseFragment<FragmentCommunitySelectCategoryBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunitySelectCategoryBinding {
        return FragmentCommunitySelectCategoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setNextButton()
        setCategorySelectButton()
        writePosting()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setTitle("글 작성하기")
            setBackButton{

            }
        }
        setTopNotice()
    }

    private fun setTopNotice() {
        binding.textviewSelectCategory.run {
            val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
            val spannableString = text.toSpannable()
            spannableString.setSpan(CustomTypefaceSpan(typeface), 0,7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            text = spannableString
        }
    }

    private fun setCategorySelectButton() {
        binding.radiogroupCategory.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                binding.btnNext.changeIsActivie(true)
            }
        })
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            isActive.observe(viewLifecycleOwner) { isActive ->
                if(isActive) {
                    setCommunityUsableButton()
                } else {
                    setUnUsableButton()
                }
            }
        }
    }

    private fun writePosting() {
        binding.btnNext.onClick {
            requireParentFragment().requireParentFragment().findNavController().navigate(R.id.action_mainFrameFragment_to_writePostingFragment)
        }
    }
}