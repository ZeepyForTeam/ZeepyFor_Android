package com.example.zeepyforandroid.review.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemReviewChoiceBinding
import com.example.zeepyforandroid.review.data.entity.ReviewChoiceModel

class ReviewChoiceAdapter(val listener: (Map<Int,Int>)-> Unit) : RecyclerView.Adapter<ReviewChoiceAdapter.ReviewChoiceViewHolder>() {

    var checkedListMap = mutableMapOf<Int,Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewChoiceViewHolder {
        val binding = ItemReviewChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewChoiceViewHolder, position: Int) {
        val item = REVIEW_ITEM[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.apply {
            groupChoice.setOnCheckedChangeListener { group , checkId ->
                when (checkId) {
                    btnLike.id -> {
                        btnDislike.isChecked = false
                        btnSoso.isChecked = false
                        checkedListMap[position] = checkId
                    }
                    btnDislike.id -> {
                        btnLike.isChecked = false
                        btnSoso.isChecked = false
                        checkedListMap[position] = checkId
                    }
                    btnSoso.id -> {
                        btnDislike.isChecked = false
                        btnLike.isChecked = false
                        checkedListMap[position] = checkId
                    }
                    else -> {
                        btnLike.isChecked = false
                        btnDislike.isChecked = false
                        btnSoso.isChecked = false
                        checkedListMap.remove(position)
                    }
                }
                Log.e("map list", checkedListMap.values.toString())
                listener(checkedListMap)
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