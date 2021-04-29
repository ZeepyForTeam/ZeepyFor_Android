package com.example.zeepyforandroid.review.data.source


import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.review.data.dto.LessorPersonalityModel

class LessorPersonalityDataSource {
    fun getLessorPersonality(): List<LessorPersonalityModel> {
        return listOf<LessorPersonalityModel>(
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
}