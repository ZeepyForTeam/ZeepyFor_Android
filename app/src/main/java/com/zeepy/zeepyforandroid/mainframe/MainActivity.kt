package com.zeepy.zeepyforandroid.mainframe

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ActivityMainBinding
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var pref: SharedPreferencesManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    var initialCommunityType: String? = null
    var initialLookAroundType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        disableDarkMode()
        initNavController()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun getDeviceSize(): List<Int> {
        var deviceWidth = 0
        var deviceHeight = 0
        val outMetrics = DisplayMetrics()
        //defaultDisplay Deprecated로 인한 Version 처리

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
        }
        return listOf(deviceWidth, deviceHeight)
    }
}