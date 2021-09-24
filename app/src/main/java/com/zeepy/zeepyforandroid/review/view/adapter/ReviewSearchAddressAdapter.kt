package com.zeepy.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemResultHouseBinding
import com.zeepy.zeepyforandroid.review.data.entity.ReviewSearchAddressModel
import com.zeepy.zeepyforandroid.util.DiffCallback

class ReviewSearchAddressAdapter: RecyclerView.Adapter<ReviewSearchAddressAdapter.ReviewSearchAddressViewHolder>() {
    private val diffcallback = DiffCallback<ReviewSearchAddressModel>()
    private val differ = AsyncListDiffer(this, diffcallback)

    fun submitList(list: List<ReviewSearchAddressModel>?) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewSearchAddressViewHolder {
        val binding = ItemResultHouseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewSearchAddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewSearchAddressViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ReviewSearchAddressViewHolder(val binding: ItemResultHouseBinding) : RecyclerView.ViewHolder(binding.root)
}