package com.example.zeepyforandroid.conditionsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemReviewOptionBinding
import com.example.zeepyforandroid.review.data.entity.OptionModel

class ConditionOptionAdapter : RecyclerView.Adapter<ConditionOptionAdapter.ConditionOptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionOptionViewHolder {
        val binding = ItemReviewOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConditionOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConditionOptionViewHolder, position: Int) {
        val item = OPTION_LIST[position]
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = OPTION_LIST.size

    companion object {
        private val OPTION_LIST = listOf<OptionModel>(
            OptionModel("에어컨"),
            OptionModel("세탁기"),
            OptionModel("침대"),
            OptionModel("옷장"),
            OptionModel("책상"),
            OptionModel("냉장고"),
            OptionModel("인덕션"),
            OptionModel("가스레인지"),
            OptionModel("전자레인지")
        )
    }

    class ConditionOptionViewHolder(val binding: ItemReviewOptionBinding) :
        RecyclerView.ViewHolder(binding.root)
}