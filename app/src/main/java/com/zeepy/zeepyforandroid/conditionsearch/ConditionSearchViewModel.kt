package com.zeepy.zeepyforandroid.conditionsearch

import android.util.Log
import android.widget.CheckBox
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.conditionsearch.data.CheckBoxModel
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

    fun getBuildingTypesObservable(): Collection<CheckBoxModel>? {
        return searchEntry.value?.buildingTypes?.values
    }

    fun getTradeTypesObservable(): Collection<CheckBoxModel>? {
        return searchEntry.value?.tradeTypes?.values
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
                ONEROOM to CheckBoxModel(1, true), TWOROOM to CheckBoxModel(2, true), OFFICETEL to CheckBoxModel(3, false)
            )
            val tradeTypes = mutableMapOf(
                MONTHLYPAYMENT to CheckBoxModel(4, true), DEPOSITPAYMENT to CheckBoxModel(5, true), TRADE to CheckBoxModel(6, false)
            )
            return SearchEntry(buildingTypes, tradeTypes, 0, 0, 0, 0)
        }



    companion object {
        const val ONEROOM = "Oneroom"
        const val TWOROOM = "Tworoom"
        const val OFFICETEL = "Officetel"
        const val MONTHLYPAYMENT = "MonthlyPayment"
        const val DEPOSITPAYMENT = "DepositPayment"
        const val TRADE = "Trade"
    }

    inner class CustomMutableLiveData<T : BaseObservable?> : MutableLiveData<T?>() {
        override fun setValue(value: T?) {
            super.setValue(value)

            //listen to property changes
            value?.addOnPropertyChangedCallback(callback)
            Log.e("what is value here?", value.toString())
        }

        var callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                //Trigger LiveData observer on change of any property in object
                Log.e("onPropertyChanged Triggered?", "yes")
                value = value
            }
        }
    }
}