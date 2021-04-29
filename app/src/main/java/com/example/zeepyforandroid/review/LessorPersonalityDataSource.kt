package com.example.zeepyforandroid.review


import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.review.dto.LessorPersonalityModel
import javax.inject.Inject

class LessorPersonalityDataSource {
    fun getLessorPersonality(): List<LessorPersonalityModel> {
        return listOf<LessorPersonalityModel>(
            LessorPersonalityModel(
                R.drawable.ic_emoji1,
                "칼 같은 우리 사이, 비즈니스형"
            ),
            LessorPersonalityModel(
                R.drawable.ic_emoji2,
                "따뜻해 녹아내리는 중!, 친절형"
            ),
            LessorPersonalityModel(
                R.drawable.ic_emoji3,
                "자유롭게만 살아다오, 방목형"
            ),
            LessorPersonalityModel(
                R.drawable.ic_emoji5,
                "겉은 바삭 속은 촉촉! 츤데레형"
            ),
            LessorPersonalityModel(
                R.drawable.ic_emoji4,
                "할말은 많지만 하지 않을래요:("
            )

        )
    }
}