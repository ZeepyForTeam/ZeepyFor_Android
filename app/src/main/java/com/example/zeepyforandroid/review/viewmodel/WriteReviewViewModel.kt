package com.example.zeepyforandroid.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.review.dto.ReviewSearchAddressModel


class WriteReviewViewModel : ViewModel() {
    private val _houseListSearched= MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    fun changeHouseListSearched(list: List<ReviewSearchAddressModel>){
        _houseListSearched.postValue(list)
    }

    //Todo: api 연결하면 더미데이터 지우기
    fun loadDummyData() {
        val dummyHouseList = mutableListOf<ReviewSearchAddressModel>()
        dummyHouseList.apply {
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성",
                    "빌라",
                    "투룸",
                    "3층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "헤라 팰리스",
                    null,
                    "주단태 이녀석",
                    "시즌 3를 기대해주세요",
                    "펜트하우스",
                    "25룸 + 비밀통로",
                    "128층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "정보처리기사",
                    null,
                    "실기시험",
                    "4월 24일",
                    "중학교",
                    "13고사장",
                    "2층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "Zeepy 달려!",
                    null,
                    "안드로이드 뷰공장 가동중",
                    "running",
                    "아파트",
                    "쓰리룸",
                    "3층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성",
                    "빌라",
                    "투룸",
                    "3층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성",
                    "빌라",
                    "투룸",
                    "3층"
                )
            )
            add(
                ReviewSearchAddressModel(
                    "금성 토성빌",
                    null,
                    "핸드피스 너무 착해요",
                    "금성 토성 화성 목성",
                    "빌라",
                    "투룸",
                    "3층"
                )
            )

        }
        changeHouseListSearched(dummyHouseList)
    }

}