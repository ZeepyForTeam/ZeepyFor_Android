package com.zeepy.zeepyforandroid.util

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.zeepy.zeepyforandroid.R

object ChromeTabHelper {
    fun launchChromeTab(activity: AppCompatActivity, url: String) {

        val builder = CustomTabsIntent.Builder()

        builder.run {
            setToolbarColor(activity.getColor(R.color.white))
            setShowTitle(true)
        }

        val intent = builder.build();
        intent.launchUrl(activity, Uri.parse(url));
    }
}