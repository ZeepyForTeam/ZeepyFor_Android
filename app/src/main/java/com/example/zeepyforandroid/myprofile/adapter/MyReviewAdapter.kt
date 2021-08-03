package com.example.zeepyforandroid.myprofile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.databinding.ItemReviewSummaryBinding
import com.example.zeepyforandroid.myprofile.data.MyReviewData
import com.example.zeepyforandroid.review.data.entity.AddressModel
import com.example.zeepyforandroid.review.view.adapter.AddressAdapter
import com.example.zeepyforandroid.review.view.adapter.AddressAdapter.Companion.diffCallback

//class MyReviewAdapter(private var reviewList: List<MyReviewData>): RecyclerView.Adapter<MyReviewAdapter.MyReviewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewHolder {
//        val binding = ItemReviewSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyReviewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyReviewHolder, position: Int) {
//        val review = reviewList[position]
//        holder.bind(review)
//    }
//
//    override fun getItemCount(): Int = reviewList.size
//
//    class MyReviewHolder(private val binding: ItemReviewSummaryBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(review: MyReviewData) {
//            binding.tvBuildingName.text = review.buildingName
//            binding.tvDatetime.text = review.dateTime
//            binding.tvBuildingOwnerReview.text = review.lessorReview
//            binding.tvHouseReview.text = review.houseReview.toString() //TODO: 각 원소 고려
//        }
//    }
//
//    fun submitList(newData: List<MyReviewData>) {
//        reviewList = newData
//        notifyDataSetChanged()
//    }
//
//}

class MyReviewAdapter : ListAdapter<MyReviewData, MyReviewAdapter.MyReviewViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyReviewAdapter.MyReviewViewHolder {
        return MyReviewViewHolder(
            ItemReviewSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: MyReviewAdapter.MyReviewViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    inner class MyReviewViewHolder(private val binding: ItemReviewSummaryBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: MyReviewData) {
            binding.tvBuildingName.text = review.buildingName
            binding.tvDatetime.text = review.dateTime
            binding.tvBuildingOwnerReview.text = review.lessorReview
            binding.tvHouseReview.text = review.houseReview.toString() //TODO: 각 원소 고려
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<MyReviewData>(){
            override fun areItemsTheSame(oldItem: MyReviewData, newItem: MyReviewData): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: MyReviewData, newItem: MyReviewData): Boolean {
                return oldItem == newItem
            }
        }
    }

}