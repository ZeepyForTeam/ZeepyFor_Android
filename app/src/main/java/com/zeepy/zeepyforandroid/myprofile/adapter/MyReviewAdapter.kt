package com.zeepy.zeepyforandroid.myprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemReviewSummaryBinding
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTO
import com.zeepy.zeepyforandroid.util.DiffCallback

class MyReviewAdapter(val listener: (SimpleReviewDTO) -> Unit): ListAdapter<SimpleReviewDTO, MyReviewAdapter.MyReviewViewHolder>(
    DiffCallback<SimpleReviewDTO>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding = ItemReviewSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener{
            listener(item)
        }
    }

    class MyReviewViewHolder(val binding: ItemReviewSummaryBinding): RecyclerView.ViewHolder(binding.root)
}