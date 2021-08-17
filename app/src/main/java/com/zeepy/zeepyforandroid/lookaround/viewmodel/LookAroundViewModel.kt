package com.zeepy.zeepyforandroid.lookaround.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.PictureModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.ReviewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LookAroundViewModel @Inject constructor(): BaseViewModel() {
    private val _buildingList = MutableLiveData<List<BuildingSummaryModel>>()
    val buildingList: LiveData<List<BuildingSummaryModel>>
        get() = _buildingList

    init {
        setDummyBuildings()
    }

    private fun setDummyBuildings() {
        val dummy = mutableListOf<BuildingSummaryModel>()
        val pictures = listOf<PictureModel>(
            PictureModel("https://picsum.photos/${Random.nextInt(100, 300)}"),
            PictureModel("https://picsum.photos/${Random.nextInt(100, 300)}"),
            PictureModel("https://picsum.photos/${Random.nextInt(100, 300)}"),
            PictureModel("https://picsum.photos/${Random.nextInt(100, 300)}")
        )
        dummy.apply {
            add(
                BuildingSummaryModel("금호빌딩",
                    "pic",
                    "친절함",
                    "깨끗하고 좋음",
                    "빌라",
                    "투룸",
                    "전세",
                    "4층",
                    listOf<OptionModel>(
                        OptionModel(R.string.airconditional),
                        OptionModel(R.string.microwave)
                    ),
                    pictures,
                    listOf<ReviewModel>(
                        ReviewModel("도로롱", "302호", "2021-08-02 11:00", "비즈니스형", "깔끔하다ㅏㅏㅏㅏㅏㅏㅏㅏㅏ", pictures)
                    )
                )
            )
            add(
                BuildingSummaryModel("금호빌딩",
                    "pic",
                    "친절함",
                    "깨끗하고 좋음",
                    "빌라",
                    "투룸",
                    "전세",
                    "4층",
                    listOf<OptionModel>(
                        OptionModel(R.string.airconditional),
                        OptionModel(R.string.microwave)
                    ),
                    pictures,
                    listOf<ReviewModel>(
                        ReviewModel("도로롱", "302호", "2021-08-02 11:00", "비즈니스형", "깔끔하다ㅏㅏㅏㅏㅏㅏㅏㅏㅏ", pictures)
                    )
                )
            )
            add(
                BuildingSummaryModel("금호빌딩",
                    "pic",
                    "친절함",
                    "깨끗하고 좋음",
                    "빌라",
                    "투룸",
                    "전세",
                    "4층",
                    listOf<OptionModel>(
                        OptionModel(R.string.airconditional),
                        OptionModel(R.string.microwave)
                    ),
                    pictures,
                    listOf<ReviewModel>(
                        ReviewModel("도로롱", "302호", "2021-08-02 11:00", "비즈니스형", "깔끔하다ㅏㅏㅏㅏㅏㅏㅏㅏㅏ", pictures)
                    )
                )
            )
        }
        _buildingList.value = dummy
    }

}