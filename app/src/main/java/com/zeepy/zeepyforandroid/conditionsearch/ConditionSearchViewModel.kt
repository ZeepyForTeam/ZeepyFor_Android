package com.zeepy.zeepyforandroid.conditionsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.conditionsearch.data.CheckBoxModel
import com.zeepy.zeepyforandroid.conditionsearch.data.SearchEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConditionSearchViewModel @Inject constructor(): ViewModel() {
    private val _searchEntry = MutableLiveData<SearchEntry>()
    val searchEntry: LiveData<SearchEntry>
        get() = _searchEntry

    init {
        _searchEntry.value = emptyEntry
    }

    /**
     * 초기 조건검색 상태
     */
    private val emptyEntry: SearchEntry
        get() {
            val buildingTypes = mutableMapOf(
                ONEROOM to CheckBoxModel(), TWOROOM to CheckBoxModel(), OFFICETEL to CheckBoxModel(false)
            )
            val tradeTypes = mutableMapOf(
                MONTHLYPAYMENT to CheckBoxModel(), DEPOSITPAYMENT to CheckBoxModel(), TRADE to CheckBoxModel(false)
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
}