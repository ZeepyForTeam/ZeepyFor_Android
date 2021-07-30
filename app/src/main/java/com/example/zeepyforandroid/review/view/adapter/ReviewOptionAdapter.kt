package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ItemReviewOptionBinding
import com.example.zeepyforandroid.review.data.entity.OptionModel

class ReviewOptionAdapter : RecyclerView.Adapter<ReviewOptionAdapter.ReviewOptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewOptionViewHolder {
        val binding = ItemReviewOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewOptionViewHolder, position: Int) {
        val item = OPTION_LIST[position]

        holder.binding.apply {
            setVariable(BR.data, item)
            checkbox.setTextColor()
        }
    }

    override fun getItemCount() = OPTION_LIST.size

    companion object {
        private val OPTION_LIST = listOf<OptionModel>(
            OptionModel(R.string.airconditional),
            OptionModel(R.string.wasingmachine),
            OptionModel(R.string.bed),
            OptionModel(R.string.closet),
            OptionModel(R.string.desk),
            OptionModel(R.string.refridgerator),
            OptionModel(R.string.induction),
            OptionModel(R.string.burner),
            OptionModel(R.string.microwave)
        )
    }

    class ReviewOptionViewHolder(val binding: ItemReviewOptionBinding) : RecyclerView.ViewHolder(binding.root)
}