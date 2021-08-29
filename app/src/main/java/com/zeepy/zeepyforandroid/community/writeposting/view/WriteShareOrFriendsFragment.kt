package com.zeepy.zeepyforandroid.community.writeposting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.community.writeposting.viewmodel.WriteShareOrFriendsViewModel
import com.zeepy.zeepyforandroid.databinding.FragmentWriteShareOrFriendsBinding

class WriteShareOrFriendsFragment: BaseFragment<FragmentWriteShareOrFriendsBinding>() {
    private val viewModel: WriteShareOrFriendsViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteShareOrFriendsBinding {
        return FragmentWriteShareOrFriendsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.toolbarWriteShareOrFriends.apply {
            setTitle("글 작성하기")
            setBackButton{

            }
        }
    }
}