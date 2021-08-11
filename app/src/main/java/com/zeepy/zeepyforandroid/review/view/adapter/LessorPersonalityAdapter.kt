package com.zeepy.zeepyforandroid.review.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ItemLessorPersonalityBinding
import com.zeepy.zeepyforandroid.review.data.entity.LessorPersonalityModel
import kotlin.properties.Delegates

class LessorPersonalityAdapter(val context: Context, val mSelected: (Int) -> Unit): RecyclerView.Adapter<LessorPersonalityAdapter.LessorPersonalityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessorPersonalityViewHolder {
        val binding = ItemLessorPersonalityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessorPersonalityViewHolder(binding)
    }

    private var mSelectedItem by Delegates.observable(-1) { property, oldValue, newValue ->
      for (i in LESSOR_PERSONALITY.indices) {
          notifyItemChanged(oldValue)
          notifyItemChanged(newValue)
      }
    }

    override fun onBindViewHolder(holder: LessorPersonalityViewHolder, position: Int) {
        val item = LESSOR_PERSONALITY[position]
        holder.binding.setVariable(BR.data, item)

        holder.binding.root.setOnClickListener {
            mSelectedItem = position
            mSelected(item.personality)
        }
        item.isSelected = mSelectedItem == position
    }

    override fun getItemCount() = LESSOR_PERSONALITY.size

    companion object {
        private val LESSOR_PERSONALITY = listOf<LessorPersonalityModel>(
            LessorPersonalityModel(
                R.drawable.selector_emoji1,
                R.string.lessor_business
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji2,
                R.string.lessor_kind
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji3,
                R.string.lessor_graze
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji5,
                R.string.lessor_softy
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji4,
                R.string.lessor_bad
            )
        )
    }

    class LessorPersonalityViewHolder(val binding: ItemLessorPersonalityBinding): RecyclerView.ViewHolder(binding.root)
}