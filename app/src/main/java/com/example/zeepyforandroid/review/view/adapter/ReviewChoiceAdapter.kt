package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemReviewChoiceBinding
import com.example.zeepyforandroid.review.data.dto.ReviewChoiceModel
import kotlin.properties.Delegates

class ReviewChoiceAdapter: RecyclerView.Adapter<ReviewChoiceAdapter.ReviewChoiceViewHolder>() {
    val datas = listOf<ReviewChoiceModel>(
        ReviewChoiceModel("방음"),   ReviewChoiceModel("해충"),   ReviewChoiceModel("채광"),   ReviewChoiceModel("수압")
    )



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewChoiceViewHolder {
        val binding = ItemReviewChoiceBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ReviewChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewChoiceViewHolder, position: Int) {
        val item = datas[position]
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = 4

    class ReviewChoiceViewHolder(val binding: ItemReviewChoiceBinding): RecyclerView.ViewHolder(binding.root)
}