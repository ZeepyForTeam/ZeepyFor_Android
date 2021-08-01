package com.example.zeepyforandroid.review.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ItemReviewChoiceBinding
import com.example.zeepyforandroid.review.data.entity.ReviewChoiceModel

class ReviewChoiceAdapter(val listener: (Map<String,Int>)-> Unit) : RecyclerView.Adapter<ReviewChoiceAdapter.ReviewChoiceViewHolder>() {

    var checkedListMap = mutableMapOf<String,Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewChoiceViewHolder {
        val binding = ItemReviewChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewChoiceViewHolder, position: Int) {
        val item = REVIEW_ITEM[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.apply {
            groupChoice.setOnCheckedChangeListener { group , checkId ->
                when(checkId) {
                    btnLike.id -> checkedListMap[item.key] = R.string.review_good
                    btnSoso.id -> checkedListMap[item.key] = R.string.review_soso
                    btnDislike.id -> checkedListMap[item.key] = R.string.review_bad
                }
                Log.e("map list", checkedListMap.values.toString())
                listener(checkedListMap)
            }
        }
    }

    override fun getItemCount() = REVIEW_ITEM.size

    companion object {
        private val REVIEW_ITEM = listOf<ReviewChoiceModel>(
            ReviewChoiceModel("방음", "soundInsulation"),
            ReviewChoiceModel("해충", "pest"),
            ReviewChoiceModel("채광", "lightning"),
            ReviewChoiceModel("수압","waterPressure")
        )
    }

    class ReviewChoiceViewHolder(val binding: ItemReviewChoiceBinding) : RecyclerView.ViewHolder(binding.root)
}