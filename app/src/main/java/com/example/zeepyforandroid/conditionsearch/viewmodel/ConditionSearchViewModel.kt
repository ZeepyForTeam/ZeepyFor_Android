package com.example.zeepyforandroid.conditionsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.conditionsearch.data.SliderState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConditionSearchViewModel @Inject constructor() : ViewModel() {

    private val _depositSliderState: MutableLiveData<SliderState> = MutableLiveData()
    val depositSliderState: LiveData<SliderState>
        get() = _depositSliderState

    private val _monthlyPaySliderState: MutableLiveData<SliderState> = MutableLiveData()
    val monthlyPaySliderState: LiveData<SliderState>
        get() = _monthlyPaySliderState

    fun setSliderState(sliderType: String, newState: SliderState) {
        when (sliderType) {
            "deposit" -> _depositSliderState.value = newState
            "monthlyPay" -> _monthlyPaySliderState.value = newState
            else -> throw IllegalArgumentException("Invalid slider type!")
        }
    }

}

