package com.zeepy.zeepyforandroid.mainframe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.address.AddressDataSource
import com.zeepy.zeepyforandroid.address.ResponseAddressDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFrameViewModel : ViewModel() {
    private val _pageIdx: MutableLiveData<Int> = MutableLiveData(0)
    val pageIdx: LiveData<Int>
        get() = _pageIdx

    fun changePageIdx(idx: Int){
        if (pageIdx.value != idx) _pageIdx.value = idx
    }
}