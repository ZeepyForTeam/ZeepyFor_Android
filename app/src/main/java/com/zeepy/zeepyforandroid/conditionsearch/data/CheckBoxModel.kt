package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zeepy.zeepyforandroid.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckBoxModel(
    private var _checked : Boolean = true
): BaseObservable(), Parcelable {
    @get:Bindable
    var checked: Boolean
        get() = _checked
        set(value) {
            Log.e("changed checkbox", value.toString())
            _checked = value
            notifyPropertyChanged(BR.checked)
        }

}
