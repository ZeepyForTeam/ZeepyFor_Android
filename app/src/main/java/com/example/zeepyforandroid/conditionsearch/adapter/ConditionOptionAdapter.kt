package com.example.zeepyforandroid.conditionsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
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

    class ConditionOptionViewHolder(val binding: ItemReviewOptionBinding) :
        RecyclerView.ViewHolder(binding.root)
}