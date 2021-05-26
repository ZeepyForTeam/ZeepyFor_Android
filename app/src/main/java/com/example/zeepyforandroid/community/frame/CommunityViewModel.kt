package com.example.zeepyforandroid.community.frame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommunityViewModel: ViewModel(){
    var _addressSearchQuery = MutableLiveData<String>()
}