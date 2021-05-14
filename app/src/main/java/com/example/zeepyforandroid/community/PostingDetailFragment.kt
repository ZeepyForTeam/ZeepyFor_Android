package com.example.zeepyforandroid.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentPostingDetailBinding

class PostingDetailFragment : BaseFragment<FragmentPostingDetailBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostingDetailBinding {
        return FragmentPostingDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setParticipaitonButton()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("커뮤니티")
            setRightButton(R.drawable.btn_like){

            }
            setBackButton{

            }
        }
    }

    private fun setParticipaitonButton() {
        binding.btnParticipation.apply {
            setText("공구 참여하기")
            setParticipationButton()
            onClick{

            }
        }
    }
}