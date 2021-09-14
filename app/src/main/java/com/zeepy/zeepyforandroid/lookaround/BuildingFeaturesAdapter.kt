package com.zeepy.zeepyforandroid.lookaround


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemBuildingFeatureBinding
import com.zeepy.zeepyforandroid.enum.BuildingType
import com.zeepy.zeepyforandroid.enum.RoomCount
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingFeatureModel


class BuildingFeaturesAdapter(val context: Context, _features: List<BuildingFeatureModel>) : RecyclerView.Adapter<BuildingFeaturesAdapter.BuildingFeaturesViewHolder>() {
    private val features: List<BuildingFeatureModel> = _features

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingFeaturesViewHolder {
        val binding = ItemBuildingFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BuildingFeaturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuildingFeaturesViewHolder, position: Int) {
        val item = features[position]
        if (item.type == "RoomCount") {
            holder.binding.tvFeature.text = context.getString(RoomCount.findRoomCountFromString(item.feature))
        } else if (item.type == "BuildingType") {
            if (item.feature != "UNKNOWN") {
                holder.binding.tvFeature.text = context.getString(BuildingType.findBuildingTypeFromString(item.feature))
            }
        }
    }

    override fun getItemCount() = features.size

    inner class BuildingFeaturesViewHolder(val binding: ItemBuildingFeatureBinding) : RecyclerView.ViewHolder(binding.root)
}