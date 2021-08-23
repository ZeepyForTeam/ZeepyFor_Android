package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zeepy.zeepyforandroid.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchEntry(
    var _buildingTypes: Map<String, RadioButtonModel>,
    var _tradeTypes: Map<String, RadioButtonModel>,
    var depositPaymentStart: Int,
    var depositPaymentEnd: Int,
    var monthlyPaymentStart: Int,
    var monthlyPaymentEnd: Int
): BaseObservable(), Parcelable {
    @get:Bindable
    var buildingTypes: Map<String, RadioButtonModel>
        get() = _buildingTypes
        set(value) {
            _buildingTypes = value
            notifyPropertyChanged(BR.buildingTypes)
        }
    @get:Bindable
    var tradeTypes: Map<String, RadioButtonModel>
        get() = _tradeTypes
        set(value) {
            _tradeTypes = value
            notifyPropertyChanged(BR.tradeTypes)
        }
}
