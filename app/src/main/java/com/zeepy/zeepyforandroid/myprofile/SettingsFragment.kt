package com.zeepy.zeepyforandroid.myprofile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentSettingsBinding
import com.zeepy.zeepyforandroid.review.viewmodel.WriteReviewViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()

        binding.tvAlarmSettingsChange.setOnClickListener {
            context?.openAppSystemSettings()
        }
    }

    private fun Context.openAppSystemSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("환경설정")
    }
}