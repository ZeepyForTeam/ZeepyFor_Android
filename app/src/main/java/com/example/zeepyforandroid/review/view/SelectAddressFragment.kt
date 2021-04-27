package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSelectAddressBinding
import com.example.zeepyforandroid.review.adapter.AddressAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.example.zeepyforandroid.util.ItemDecoration


class SelectAddressFragment : BaseFragment<FragmentSelectAddressBinding>() {
    private val viewModel: WriteReviewViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectAddressBinding {
        return FragmentSelectAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setDatas()
        registerAddress()
    }

    private fun initView() {
        val span = binding.tvSelectAddress.text
        val typeface = Typeface.create(
            ResourcesCompat.getFont(
                requireContext(),
                R.font.nanum_square_round_extrabold
            ), Typeface.NORMAL
        )
        span.toSpannable()
            .setSpan(CustomTypefaceSpan(typeface), 0, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton {
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
        binding.btnNext.run {
            setText("다음으로")
            onClick {

            }
        }

        binding.rvAddressList.run {
            adapter = AddressAdapter {
                viewModel.deleteAddress(it)
                Log.e("list", viewModel.addressList.value.toString())
            }
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun setDatas() {
        (binding.rvAddressList.adapter as AddressAdapter).submitList(viewModel.addressList.value)
    }

    private fun registerAddress() {
        binding.tvRegisterAddress.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_selectAddressFragment_to_writeDetailAddressFragment)
        }
    }
}