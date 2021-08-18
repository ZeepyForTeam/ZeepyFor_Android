package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchEntry(
    var _buildingTypes: Map<String, CheckBoxModel>,
    var _tradeTypes: Map<String, CheckBoxModel>,
    var depositPaymentStart: Int,
    var depositPaymentEnd: Int,
    var monthlyPaymentStart: Int,
    var monthlyPaymentEnd: Int
): BaseObservable(), Parcelable {
    @get:Bindable
    var buildingTypes: Map<String, CheckBoxModel>
        get() = _buildingTypes
        set(value) {
            _buildingTypes = value
            notifyPropertyChanged(BR.buildingTypes)
        }
    @get:Bindable
    var tradeTypes: Map<String, CheckBoxModel>
        get() = _tradeTypes
        set(value) {
            _tradeTypes = value
            notifyPropertyChanged(BR.tradeTypes)
        }
}
