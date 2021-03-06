package com.example.zeepyforandroid.community.searchaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.community.frame.viewmodel.CommunityFrameViewModel
import com.example.zeepyforandroid.databinding.FragmentCommunitySearchAddressBinding

class CommunitySearchAddressFragment : BaseFragment<FragmentCommunitySearchAddressBinding>() {
    private val viewModel by activityViewModels<CommunityFrameViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunitySearchAddressBinding {
        return FragmentCommunitySearchAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewTheme()
        goToWriteDetailAddress()
    }

    private fun initViewTheme() {
        binding.layoutSearchAddress.apply {
            btnNext.apply {
                setCommunityUsableButton()
                setText("다음으로")
            }
            etSearchAddress.apply {
                val searchIcon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_search_community)
                setBackgroundResource(R.drawable.edittext_community)
                setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null)
            }
            buttonSearch.setTextColor(ContextCompat.getColor(requireContext(), R.color.zeepy_green_33))
        }
        binding.toolbar.apply {
            setTitle("커뮤니티")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }

    private fun goToWriteDetailAddress() {
        binding.layoutSearchAddress.btnNext.setOnClickListener {
            viewModel.changeSearchAddressQuery(binding.layoutSearchAddress.etSearchAddress.text.toString())
            findNavController().navigate(R.id.action_communitySearchAddressFragment_to_communityDetailAddressFragment)
        }
    }

}