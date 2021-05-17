package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ItemLessorPersonalityBinding
import com.example.zeepyforandroid.review.data.entity.LessorPersonalityModel
import kotlin.properties.Delegates

class LessorPersonalityAdapter(val mSelected: (Int) -> Unit): RecyclerView.Adapter<LessorPersonalityAdapter.LessorPersonalityViewHolder>() {
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
            mSelected(mSelectedItem)
        }
        item.isSelected = mSelectedItem == position
    }

    override fun getItemCount() = LESSOR_PERSONALITY.size

    companion object {
        private val LESSOR_PERSONALITY = listOf<LessorPersonalityModel>(
            LessorPersonalityModel(
                R.drawable.selector_emoji1,
                "칼 같은 우리 사이, 비즈니스형"
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji2,
                "따뜻해 녹아내리는 중!, 친절형"
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji3,
                "자유롭게만 살아다오, 방목형"
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji5,
                "겉은 바삭 속은 촉촉! 츤데레형"
            ),
            LessorPersonalityModel(
                R.drawable.selector_emoji4,
                "할말은 많지만 하지 않을래요:("
            )
        )
    }

    class LessorPersonalityViewHolder(val binding: ItemLessorPersonalityBinding): RecyclerView.ViewHolder(binding.root)
}