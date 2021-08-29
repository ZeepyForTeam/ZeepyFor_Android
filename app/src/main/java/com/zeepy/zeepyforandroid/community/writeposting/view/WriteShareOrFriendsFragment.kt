package com.zeepy.zeepyforandroid.community.writeposting.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.writeposting.viewmodel.WriteShareOrFriendsViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentWriteShareOrFriendsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteShareOrFriendsFragment: BaseFragment<FragmentWriteShareOrFriendsBinding>() {
    private val viewModel: WriteShareOrFriendsViewModel by viewModels()
    private val args: WriteShareOrFriendsFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteShareOrFriendsBinding {
        return FragmentWriteShareOrFriendsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initView()
        viewModel.changePostingType(args.postingType)
        usableNextButton()
    }

    private fun initView() {
        binding.toolbarWriteShareOrFriends.apply {
            setTitle("글 작성하기")
            setBackButton{
                findNavController().popBackStack()
            }
        }
        binding.buttonNext.apply {
            setText("다음으로")
            setUnUsableButton()
            onClick{
                viewModel.sendRequestData()
                Log.e("dakfj","${viewModel.requestWritePosting.value}")
                val action = WriteShareOrFriendsFragmentDirections
                    .actionWriteShareOrFriendsFragmentToCommunityLoadPictureFragment(viewModel.requestWritePosting.value)
                findNavController().navigate(action)
            }
        }
    }

    private fun usableNextButton() {
        val inputList = listOf(viewModel.title, viewModel.content)
        inputList.forEach {
            it.observe(viewLifecycleOwner) {
                if (viewModel.checkInputEveryField()) {
                    binding.buttonNext.setCommunityUsableButton()
                } else {
                    binding.buttonNext.setUnUsableButton()
                }
            }
        }
    }
}