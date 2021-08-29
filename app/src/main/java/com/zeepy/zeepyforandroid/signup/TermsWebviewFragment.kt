package com.zeepy.zeepyforandroid.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentTermsWebviewBinding
import java.lang.IllegalArgumentException

class TermsWebviewFragment: BaseFragment<FragmentTermsWebviewBinding>() {
    private val args: TermsWebviewFragmentArgs by navArgs()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTermsWebviewBinding {
        return FragmentTermsWebviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(args.webviewType) {
            WebViewType.PERSONAL_INFO.name -> moveToWebView(binding.webviewTerms, WebViewType.PERSONAL_INFO.url)
            WebViewType.ZEEPY_TERMS.name  -> moveToWebView(binding.webviewTerms, WebViewType.ZEEPY_TERMS.url)
            else -> throw IllegalArgumentException("error args type")
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun moveToWebView(webView: WebView, url: String) {
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(url)
    }

    companion object {
        enum class WebViewType(val url: String){
            PERSONAL_INFO("https://zeepy.creatorlink.net/개인정보처리방침"),
            ZEEPY_TERMS("https://zeepy.creatorlink.net/이용약관");
        }
    }
}