package com.example.zeepyforandroid

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toFile
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import coil.load
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.example.zeepyforandroid.util.FontType
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import java.io.File

@BindingAdapter("loadUrl")
fun loadUrl(imageView: ImageView, url: String?) {
    if (url == null) {
        imageView.load(R.drawable.ic_launcher_background)
    } else {
        imageView.load(url)
    }
}

@BindingAdapter("loadDrawable")
fun loadDrawable(imageView: ImageView, drawable: Int?) {
    if (drawable == null) {
        imageView.load(R.drawable.ic_launcher_background)
    } else {
        imageView.load(drawable)
    }
}

@BindingAdapter("load_uri")
fun ImageView.loadFromStorage(uri: Uri) {
    load(uri)
}

@BindingAdapter("load_file")
fun ImageView.loadFileFromStorage(uri: Uri) {
    load(uri.toFile())
}

@BindingAdapter("compound_button_string_resource")
fun CompoundButton.setTextWithStringResources(resourceId: Int) {
    text = resources.getString(resourceId)
}

@BindingAdapter("text_with_string_resources")
fun TextView.setTextWithStringResources(resourceId: Int) {
    text = resources.getString(resourceId)
}

@BindingAdapter("android:text")
fun setText(textView: TextView, content: MutableLiveData<String>?) {
    if (content == null) {
        textView.text = ""
    } else {
        if (textView.text != content) textView.text = content.value
    }
}

@InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
fun getText(editText: EditText): String {
    return editText.text.toString()
}

@BindingAdapter("textAttrChanged")
fun setTextWatcher(editText: EditText, textAttrChanged: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            textAttrChanged?.onChange()
        }
    })
}

@BindingAdapter("android:checked")
fun setChecked(checkBox: CheckBox, isChecked: MutableLiveData<Boolean>?) {
    if (isChecked == null) {
        checkBox.isChecked = false
    } else {
        checkBox.isChecked = isChecked.value == true
    }
}

@BindingAdapter("checkChanged")
fun CheckBox.setOnChangedListener(checkChanged: InverseBindingListener) {
    setOnCheckedChangeListener { _, _ ->
        checkChanged?.onChange()
    }

}

@InverseBindingAdapter(attribute = "android:checked", event = "checkChanged")
fun getChecked(checkBox: CheckBox): Boolean {
    return checkBox.isChecked
}

@BindingAdapter("isSelected")
fun setIsSelected(imageView: ImageView, isSelected: Boolean) {
    imageView.isSelected = isSelected
}

@BindingAdapter("font")
fun TextView.font(type: FontType) {
    try {
        typeface = ResourcesCompat.getFont(context, type.fontRes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter("load_shapeableimage")
fun ShapeableImageView.loadShapeableImage(url: String?) {
    load(url)
}

