package com.example.zeepyforandroid.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.review.dto.ReviewSearchAddressModel


class WriteReviewViewModel : ViewModel() {
    private val _houseListSearched = MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    val searchQuery = MutableLiveData<String>()

    fun changeHouseListSearched(list: List<ReviewSearchAddressModel>){
        _houseListSearched.postValue(list)
    }
}