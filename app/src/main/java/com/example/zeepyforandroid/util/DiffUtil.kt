package com.example.zeepyforandroid.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DiffUtil<T> {
    fun diffCallback()= object : DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}