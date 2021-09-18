package com.zeepy.zeepyforandroid.lookaround.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zeepy.zeepyforandroid.databinding.ItemPictureBinding
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR


class PhotoReviewAdapter: RecyclerView.Adapter<PhotoReviewAdapter.LookAroundPhotoViewHolder>() {
    private val diffcallback = DiffCallback<String>()
    private val differ = AsyncListDiffer(this, diffcallback)

    fun submitList(list: List<String>?) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LookAroundPhotoViewHolder {
        val binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LookAroundPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LookAroundPhotoViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.setVariable(BR.data, item)

        holder.binding.picture.load(item)
    }

    override fun getItemCount() = differ.currentList.size

    inner class LookAroundPhotoViewHolder(val binding: ItemPictureBinding) : RecyclerView.ViewHolder(binding.root)
}