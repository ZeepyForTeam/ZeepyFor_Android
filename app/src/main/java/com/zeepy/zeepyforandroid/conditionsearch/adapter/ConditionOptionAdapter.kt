package com.zeepy.zeepyforandroid.conditionsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ItemReviewOptionBinding
import com.zeepy.zeepyforandroid.review.data.entity.OptionModel

class ConditionOptionAdapter(val listener: SelectOptionInterface): RecyclerView.Adapter<ConditionOptionAdapter.ConditionOptionViewHolder>() {

    interface SelectOptionInterface {
        fun select(option: Int)
        fun unselect(option: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionOptionViewHolder {
        val binding = ItemReviewOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConditionOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConditionOptionViewHolder, position: Int) {
        val item = OPTION_LIST[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.checkbox.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                listener.select(item.option)
            } else {
                listener.unselect(item.option)
            }
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

    class ConditionOptionViewHolder(val binding: ItemReviewOptionBinding) :
        RecyclerView.ViewHolder(binding.root)
}