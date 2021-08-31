package com.zeepy.zeepyforandroid.lookaround.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.PictureModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.ReviewModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LookAroundViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val searchAddressListRepository: SearchAddressListRepository
): BaseViewModel() {
    private val _buildingList = MutableLiveData<List<BuildingSummaryModel>>()
    val buildingList: LiveData<List<BuildingSummaryModel>>
        get() = _buildingList

    private val _resultFetchedAddresses = MutableLiveData<List<SearchAddressListModel>>()
    val resultSearchedAddress: LiveData<List<SearchAddressListModel>>
        get() = _resultFetchedAddresses

    init {
        setDummyBuildings()
    }

    /**
     * 현재 주소를 기준으로 빌딩 리스트 가져오기
     */
    fun searchBuildingAddress(address: String) {
        addDisposable(
            searchAddressListRepository.searchBuildingAddressList(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _resultFetchedAddresses.postValue(response)
                }, {
                    it.printStackTrace()
                })
        )
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