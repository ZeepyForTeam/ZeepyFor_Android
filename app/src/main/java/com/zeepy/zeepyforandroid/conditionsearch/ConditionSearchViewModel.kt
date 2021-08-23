package com.zeepy.zeepyforandroid.conditionsearch

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.conditionsearch.data.RadioButtonModel
import com.zeepy.zeepyforandroid.conditionsearch.data.SearchEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConditionSearchViewModel @Inject constructor(): ViewModel() {
    private val _searchEntry = CustomMutableLiveData<SearchEntry>()
    val searchEntry: CustomMutableLiveData<SearchEntry>
        get() = _searchEntry

    private val _selectedOptionList = MutableLiveData<MutableList<String>>(mutableListOf())
    val selectedOptionList: LiveData<MutableList<String>>
        get() = _selectedOptionList

    init {
        _searchEntry.value = emptyEntry
    }

    fun selectOption(option: String){
        _selectedOptionList.value?.add(option)
    }

    fun unselectOption(option: String) {
        _selectedOptionList.value?.remove(option)
    }

    /**
     * 초기 조건검색 상태
     */
    private val emptyEntry: SearchEntry
        get() {
            val buildingTypes = mutableMapOf(
                ALL to RadioButtonModel(1, true), MULTIHOUSEHOLD to RadioButtonModel(2, false), OFFICETEL to RadioButtonModel(3, false)
            )
            val tradeTypes = mutableMapOf(
                ALL to RadioButtonModel(4, true), MONTHLYPAYMENT to RadioButtonModel(5, false), DEPOSITPAYMENT to RadioButtonModel(6, false), TRADE to RadioButtonModel(7, false)
            )
            return SearchEntry(buildingTypes, tradeTypes, 0, 0, 0, 0)
        }



    companion object {
        const val ALL = "All"
        const val MULTIHOUSEHOLD = "MultiHousehold"
        const val OFFICETEL = "Officetel"
        const val MONTHLYPAYMENT = "MonthlyPayment"
        const val DEPOSITPAYMENT = "DepositPayment"
        const val TRADE = "Trade"
    }

    /**
     * 커스텀 라이브데이터 클래스 BaseObservable 상속
     * Property 변경도 observe할 수 있음
     */
    inner class CustomMutableLiveData<T : BaseObservable?> : MutableLiveData<T?>() {
        override fun setValue(value: T?) {
            super.setValue(value)

            //listen to property changes
            value?.addOnPropertyChangedCallback(callback)
        }

        var callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                //Trigger LiveData observer on change of any property in object
                value = value
            }
        }
    }
}