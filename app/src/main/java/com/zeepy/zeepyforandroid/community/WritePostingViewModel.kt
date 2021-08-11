package com.zeepy.zeepyforandroid.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WritePostingViewModel: ViewModel(){
    val title = MutableLiveData<String>()
    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<String>()
    val purchaseSite = MutableLiveData<String>()
    val dealMethod = MutableLiveData<String>()
    val targetCount = MutableLiveData<Int>()

}