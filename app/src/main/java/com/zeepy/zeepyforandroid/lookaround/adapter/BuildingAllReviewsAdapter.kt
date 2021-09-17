package com.zeepy.zeepyforandroid.lookaround.adapter


import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemBuildingReviewBinding


class BuildingAllReviewsAdapter(val listener: (ResponseReviewDTO) -> Unit): RecyclerView.Adapter<BuildingAllReviewsAdapter.BuildingAllReviewsViewHolder>() {
    private val diffCallback = DiffCallback<ResponseReviewDTO>()
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ResponseReviewDTO>?) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingAllReviewsViewHolder {
        val binding = ItemBuildingReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingAllReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildingAllReviewsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount() = differ.currentList.size

    inner class BuildingAllReviewsViewHolder(val binding: ItemBuildingReviewBinding) : RecyclerView.ViewHolder(binding.root)
}