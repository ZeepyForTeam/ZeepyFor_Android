package com.zeepy.zeepyforandroid.lookaround.adapter


import android.content.Context
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemBuildingReviewBinding
import com.zeepy.zeepyforandroid.enum.LessorAge
import com.zeepy.zeepyforandroid.enum.Preference
import com.zeepy.zeepyforandroid.util.ZeepyStringBuilder


class BuildingAllReviewsAdapter(val context: Context, val listener: (ResponseReviewDTO) -> Unit): RecyclerView.Adapter<BuildingAllReviewsAdapter.BuildingAllReviewsViewHolder>() {
    private val diffCallback = DiffCallback<ResponseReviewDTO>()
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ResponseReviewDTO>?) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingAllReviewsViewHolder {
        val binding = ItemBuildingReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingAllReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildingAllReviewsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.tvViewAllReviews.setOnClickListener {
            listener(item)
        }

        holder.binding.apply {
            tvLessorReviewOneLiner.text = ZeepyStringBuilder.buildLessorAgeAndGenderStmt(
                LessorAge.findLessorAgeFromLiteralString(item.lessorAge), item.lessorGender)
            tvSoundInsulationRating.text = context.getString(Preference.getIdFromString(item.soundInsulation))
            tvPestRating.text = context.getString(Preference.getIdFromString(item.pest))
            tvSunlightRating.text = context.getString(Preference.getIdFromString(item.lightning))
            tvWaterpressureRating.text = context.getString(Preference.getIdFromString(item.waterPressure))
        }
    }

    override fun getItemCount() = differ.currentList.size

    inner class BuildingAllReviewsViewHolder(val binding: ItemBuildingReviewBinding) : RecyclerView.ViewHolder(binding.root)
}