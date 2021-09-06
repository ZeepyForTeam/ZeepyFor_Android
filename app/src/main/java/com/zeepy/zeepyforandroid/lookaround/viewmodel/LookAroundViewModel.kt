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
import com.zeepy.zeepyforandroid.util.SingleLiveEvent
import com.zeepy.zeepyforandroid.util.ext.hasDealType
import com.zeepy.zeepyforandroid.util.ext.hasOptions
import com.zeepy.zeepyforandroid.util.ext.isWithinCost
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

    private val _selectedAddress = SingleLiveEvent<LocalAddressEntity>()
    val selectedAddress: LiveData<LocalAddressEntity>
        get() = _selectedAddress

    private val filteredBuildingList = ArrayList<BuildingSummaryModel>()

    private val _buildingListLiveData = MutableLiveData<MutableList<BuildingSummaryModel>>()
    val buildingListLiveData: LiveData<MutableList<BuildingSummaryModel>>
        get() = _buildingListLiveData

    //TODO: Use Room DB for this too
    private val _fetchedAddressList = MutableLiveData<SearchAddressForLookAroundModel>()
    val fetchedAddressList: LiveData<SearchAddressForLookAroundModel>
        get() = _fetchedAddressList

    private val _paginationIdx = MutableLiveData<Int>(0)
    val paginationIdx: LiveData<Int>
        get() = _paginationIdx

    private val _totalPages = MutableLiveData<Int>()
    val totalPages: LiveData<Int>
        get() = _totalPages

    fun changePaginationIdx(idx: Int) {
        _paginationIdx.value = idx
    }

    fun increasePageIdx() {
        var page = paginationIdx.value
        if (page != null) {
            page += 1
            _paginationIdx.value = page!!
        }
    }

    fun removeBuildingsList() {
        val buildings = _buildingListLiveData.value?.toMutableList()
        buildings?.clear()
        _buildingListLiveData.value = buildings!!
    }

    /**
     * 현재 주소를 기준으로 빌딩 리스트 가져오기
     */
    fun searchBuildingsByAddress() {
        _buildingListLiveData.value = mutableListOf()
        viewModelScope.launch {
            val result = searchAddressListRepository.searchBuildingsByAddress(selectedAddress.value?.cityDistinct!!, _paginationIdx.value!!)

            if (result?.addresses.isNullOrEmpty()) {
                _paginationIdx.value = -1
            } else {
                _totalPages.value = result?.totalPages
                _fetchedAddressList.value = result!!
                val nums = arrayListOf<Int>()
                (_fetchedAddressList.value!!.addresses.indices).forEach {
                    nums.add(_fetchedAddressList.value!!.addresses[it].id)
                }
                nums.map { num ->
                    async {
                        num to getBuildingInfoById(num)
                    }
                }.map { it.await() }
            }
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
        val monthly = conditions.dealType == "MONTHLY"
        var deposit = conditions.dealType == "JEONSE"

        filteredBuildingList.clear()
        _buildingListLiveData.value?.forEach { building ->
            if (!building.reviews.isNullOrEmpty() && !building.buildingDeals.isNullOrEmpty()) {
                if (building.buildingType == conditions.buildingType
                    && building.buildingDeals.hasDealType(conditions.dealType)
                    && building.buildingDeals.isWithinCost(monthly, conditions.monthlyPayStart, conditions.monthlyPayEnd, deposit, conditions.depositPayStart, conditions.depositPayEnd)
                    && building.reviews.hasOptions(conditions.options)) {
                    filteredBuildingList.add(building)
                }
            }
        }
        _buildingListLiveData.value = filteredBuildingList
    }

    suspend fun getBuildingInfoById(id: Int) {
        val result = buildingRepository.getBuildingsInfoById(id)
        if (result != null) {
            Log.e("what building is currently being fetched?", result.toString())
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
                    Log.e("addressList", _addressList.toString())
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