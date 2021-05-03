package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemReviewChoiceBinding
import com.example.zeepyforandroid.review.data.dto.ReviewChoiceModel

class ReviewChoiceAdapter : RecyclerView.Adapter<ReviewChoiceAdapter.ReviewChoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewChoiceViewHolder {
        val binding =
            ItemReviewChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewChoiceViewHolder, position: Int) {
        val item = REVIEW_ITEM[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.apply {
            groupChoice.setOnCheckedChangeListener { _ , checkId ->
                when (checkId) {
                    btnLike.id -> {
                        btnDislike.isSelected = false
                        btnSoso.isSelected = false
                    }
                    btnDislike.id -> {
                        btnLike.isSelected = false
                        btnSoso.isSelected = false
                    }
                    btnSoso.id -> {
                        btnDislike.isSelected = false
                        btnLike.isSelected = false
                    }
                    else -> {
                        btnDislike.isSelected = false
                        btnLike.isSelected = false
                        btnSoso.isSelected = false
                    }
                }
            }
        }
    }

    override fun getItemCount() = REVIEW_ITEM.size

    companion object {
        private val REVIEW_ITEM = listOf<ReviewChoiceModel>(
            ReviewChoiceModel("방음"),
            ReviewChoiceModel("해충"),
            ReviewChoiceModel("채광"),
            ReviewChoiceModel("수압")
        )
    }

    class ReviewChoiceViewHolder(val binding: ItemReviewChoiceBinding) : RecyclerView.ViewHolder(binding.root)
}