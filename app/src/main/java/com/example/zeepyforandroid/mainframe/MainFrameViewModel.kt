package com.example.zeepyforandroid.mainframe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFrameViewModel : ViewModel() {
    private val _pageIdx: MutableLiveData<Int> = MutableLiveData(0)
    val pageIdx: LiveData<Int>
        get() = _pageIdx

    fun changePageIdx(idx: Int){
        if (pageIdx.value != idx) _pageIdx.value = idx
    }
}