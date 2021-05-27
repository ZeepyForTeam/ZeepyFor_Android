package com.example.zeepyforandroid.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunitySearchAddressBinding

class CommunitySearchAddressFragment : BaseFragment<FragmentCommunitySearchAddressBinding>() {

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
                setCommunityTheme()
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
            setBackButton {}
        }
    }

    private fun goToWriteDetailAddress() {
        binding.layoutSearchAddress.btnNext.setOnClickListener {
            Log.e("click", "click")
            findNavController().navigate(R.id.action_communitySearchAddressFragment_to_communityDetailAddressFragment)
        }
    }

}