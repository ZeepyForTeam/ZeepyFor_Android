package com.example.zeepyforandroid.lookaround

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemPictureBinding
import com.example.zeepyforandroid.lookaround.data.entity.LookAroundBuildingSummaryModel
import com.example.zeepyforandroid.util.DiffCallback

class PhotoReviewAdapter: RecyclerView.Adapter<PhotoReviewAdapter.LookAroundPhotoViewHolder>() {
    private val diffcallback = DiffCallback<LookAroundBuildingSummaryModel>()
    private val differ = AsyncListDiffer(this, diffcallback)

    fun submitList(list: List<LookAroundBuildingSummaryModel>?) = differ.submitList(list)

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
    }

    override fun getItemCount() = differ.currentList.size

    inner class LookAroundPhotoViewHolder(val binding: ItemPictureBinding) : RecyclerView.ViewHolder(binding.root)
}