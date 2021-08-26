package com.zeepy.zeepyforandroid.mainframe

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavController()

        pref.putSharedPref("userIdx", 3)
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}