package com.zeepy.zeepyforandroid.myprofile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemReviewSummaryBinding
import com.zeepy.zeepyforandroid.enum.CommunityTendency
import com.zeepy.zeepyforandroid.enum.LessorAge
import com.zeepy.zeepyforandroid.enum.Preference
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTO
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.util.ZeepyStringBuilder

class MyReviewAdapter(val context: Context, val listener: (SimpleReviewDTO) -> Unit): ListAdapter<SimpleReviewDTO, MyReviewAdapter.MyReviewViewHolder>(
    DiffCallback<SimpleReviewDTO>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding = ItemReviewSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener{
            listener(item)
        }

        holder.binding.tvBuildingOwnerReview.text = ZeepyStringBuilder.buildLessorAgeAndGenderStmt(
            LessorAge.findLessorAgeFromLiteralString(item.lessorAge), item.lessorGender)
        holder.binding.tvLessorCommunicationReview.text = context.getString(CommunityTendency.findTendencyFromString(item.communcationTendency))
        holder.binding.tvSoundInsulationRating.text = context.getString(Preference.getIdFromString(item.soundInsulation))
        holder.binding.tvPestRating.text = context.getString(Preference.getIdFromString(item.pest))
        holder.binding.tvSunlightRating.text = context.getString(Preference.getIdFromString(item.lightning))
        holder.binding.tvWaterpressureRating.text = context.getString(Preference.getIdFromString(item.waterPressure))
    }

    class MyReviewViewHolder(val binding: ItemReviewSummaryBinding): RecyclerView.ViewHolder(binding.root)
}