package com.example.zeepyforandroid

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import coil.load

@BindingAdapter("loadUrl")
fun loadUrl(imageView:ImageView, url: String?) {
    if (url == null){
        imageView.load(R.drawable.ic_launcher_background)
    } else {
        imageView.load(url)
    }
}

@BindingAdapter("loadDrawable")
fun loadDrawable(imageView:ImageView, drawable: Int?) {
    if (drawable == null) {
        imageView.load(R.drawable.ic_launcher_background)
    } else {
        imageView.load(drawable)
    }
}

@BindingAdapter("android:text")
fun setText(textView: TextView, content: MutableLiveData<String>?){
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
    editText.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            textAttrChanged?.onChange()
        }
    })
}