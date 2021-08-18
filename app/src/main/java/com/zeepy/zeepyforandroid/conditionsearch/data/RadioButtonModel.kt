package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zeepy.zeepyforandroid.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RadioButtonModel(
    private var _id : Int,
    private var _checked : Boolean
): BaseObservable(), Parcelable {
    @get:Bindable
    var checked: Boolean
        get() = _checked
        set(value) {
            _checked = value
            notifyPropertyChanged(BR.checked)
        }
    @get:Bindable
    var id: Int
        get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }
}
