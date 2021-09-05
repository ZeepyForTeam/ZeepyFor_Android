package com.zeepy.zeepyforandroid.lookaround.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.conditionsearch.data.ConditionSetModel
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.SearchAddressForLookAroundModel
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepository
import com.zeepy.zeepyforandroid.util.SingleLiveData
import com.zeepy.zeepyforandroid.util.ext.hasDealType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LookAroundViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val searchAddressListRepository: SearchAddressListRepository,
    private val buildingRepository: BuildingRepository,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {

    private val _addressList = MutableLiveData<List<LocalAddressEntity>>(mutableListOf())
    val addressList: LiveData<List<LocalAddressEntity>>
        get() = _addressList

    private val _selectedAddress = SingleLiveData<LocalAddressEntity>()
    val selectedAddress: SingleLiveData<LocalAddressEntity>
        get() = _selectedAddress

    private val filteredBuildingList = ArrayList<BuildingSummaryModel>()

    private val _buildingListLiveData = MutableLiveData<MutableList<BuildingSummaryModel>>()
    val buildingListLiveData: LiveData<MutableList<BuildingSummaryModel>>
        get() = _buildingListLiveData

    //TODO: Use Room DB for this too
    private val _fetchedAddressList = MutableLiveData<List<SearchAddressForLookAroundModel>>()
    val fetchedAddressList: LiveData<List<SearchAddressForLookAroundModel>>
        get() = _fetchedAddressList

    init {
        getAddressListFromServer()
    }

    /**
     * 현재 주소를 기준으로 빌딩 리스트 가져오기
     */
    fun searchBuildingsByAddress(address: String) {
        _buildingListLiveData.value = mutableListOf()
        viewModelScope.launch {
            val result = searchAddressListRepository.searchBuildingsByAddress(address)
            _fetchedAddressList.value = result!!

            val nums = arrayListOf<Int>()

            (_fetchedAddressList.value!!.indices).forEach {
                nums.add(_fetchedAddressList.value!![it].id)
            }
            Log.e("nums", nums.toString())
            nums.map { num ->
                async {
                    num to getBuildingInfoById(num)
                }
            }.map { it.await() }
        }
    }

    fun setBuildingsByFiltering(lessorType: String) {
        filteredBuildingList.clear()
        _buildingListLiveData.value?.forEach { building ->
            if (!building.reviews.isNullOrEmpty()) {
                when (building.reviews[0].communcationTendency) {
                    lessorType -> filteredBuildingList.add(building)
                }
            }
        }
        _buildingListLiveData.value = filteredBuildingList
    }

    fun setBuildingsByConditions(conditions: ConditionSetModel) {
        filteredBuildingList.clear()
        _buildingListLiveData.value?.forEach { building ->
            if (!building.reviews.isNullOrEmpty() && !building.buildingDeals.isNullOrEmpty()) {
                if (building.buildingType == conditions.buildingType
                    && building.buildingDeals.hasDealType(conditions.dealType)
                    && )
            }
        }
    }

    suspend fun getBuildingInfoById(id: Int) {
        val result = buildingRepository.getBuildingsInfoById(id)
        if (result != null) {
            insertBuildingInfoToLocal(result)
            getBuildingInfoFromLocal(id)
        } else {
            getBuildingInfoFromLocal(id)
        }
    }

    suspend fun getBuildingInfoFromLocal(id: Int) {
        try {
            zeepyLocalRepository.fetchBuildingById(id).collect {
                _buildingListLiveData.plusAssign(it)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(newValue: T) {
        val value = this.value
        value?.add(newValue)
        this.value = value
    }

    suspend fun insertBuildingInfoToLocal(building: BuildingSummaryModel) {
        zeepyLocalRepository.insertBuilding(building)
    }

    fun getAddressListFromServer() {
        addDisposable(
            addressDataSource.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (!response.addresses.isNullOrEmpty()) {
                        insertAddressListToLocal(response.addresses.map { it.toLocalAddressEntity() })
                    } else {
                        fetchAddressListFromLocal()
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun fetchAddressListFromLocal() {
        addDisposable(
            zeepyLocalRepository.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ addressList ->
                    _addressList.postValue(addressList)
                    addressList.let {
                        _selectedAddress.value = it.find { address -> address.isAddressCheck }
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun insertAddressListToLocal(addressList: List<LocalAddressEntity>) {
        addDisposable(
            Observable.fromCallable {
                zeepyLocalRepository.insertAllAddress(addressList)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchAddressListFromLocal()
                }, {
                    fetchAddressListFromLocal()
                    it.printStackTrace()
                })
        )
    }

}