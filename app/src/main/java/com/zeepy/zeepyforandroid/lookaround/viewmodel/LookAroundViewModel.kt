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
import com.zeepy.zeepyforandroid.util.Event
import com.zeepy.zeepyforandroid.util.ext.hasDealType
import com.zeepy.zeepyforandroid.util.ext.hasOptions
import com.zeepy.zeepyforandroid.util.ext.isWithinCost
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
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

    private val _addressList = MutableLiveData<Event<List<LocalAddressEntity>>>(Event(mutableListOf()))
    val addressList: LiveData<Event<List<LocalAddressEntity>>>
        get() = _addressList

    private val _selectedAddress = MutableLiveData<LocalAddressEntity>()
    val selectedAddress: LiveData<LocalAddressEntity>
        get() = _selectedAddress

    private val _filteredBuildingList = MutableLiveData<MutableList<BuildingSummaryModel>>()
    val filteredBuildingList: LiveData<MutableList<BuildingSummaryModel>>
        get() = _filteredBuildingList

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

    private val _fetchedBuildingsCount = MutableLiveData<Int>(0)
    val fetchedBuildingsCount: LiveData<Int>
        get() = _fetchedBuildingsCount

    private val _isLastPage = MutableLiveData<Boolean>(false)
    val isLastPage: LiveData<Boolean>
        get() = _isLastPage

    private val _isOnFiltered = MutableLiveData<Boolean>(false)
    val isOnFiltered: LiveData<Boolean>
        get() = _isOnFiltered

    private val _filterChecked = MutableLiveData<String>()
    val filterChecked: LiveData<String>
        get() = _filterChecked

    private val _isFetchDone = MutableLiveData<Boolean>(false)
    val isFetchDone: LiveData<Boolean>
        get() = _isFetchDone

    init {
        getAddressListFromServer()
    }

    fun resetIsFetchDone() {
        _isFetchDone.value = false
    }

    fun setFilterChecked(filterName: String) {
        _filterChecked.value = filterName
    }

    fun changeFilteredStatus(flag: Boolean) {
        _isOnFiltered.value = flag
    }

    fun changeSelectedAddress(address: LocalAddressEntity) {
        _selectedAddress.value = address
    }

    fun resetIsLastPage() {
        _isLastPage.value = false
    }

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

    fun removeBuildingsList(list: MutableLiveData<MutableList<BuildingSummaryModel>>) {
        if (!list.value.isNullOrEmpty()) {
            val buildings = list.value
            buildings?.clear()
            list.value = buildings
        }
    }

    /**
     * 현재 주소를 기준으로 빌딩 리스트 가져오기
     */
    fun searchBuildingsByAddress() {
        viewModelScope.launch {
            val result = searchAddressListRepository.searchBuildingsByAddress(selectedAddress.value?.cityDistinct!!, _paginationIdx.value!!)

            when {
                result?.addresses.isNullOrEmpty() -> {
                    _paginationIdx.value = -1
                }
                else -> {
                    _totalPages.value = result?.totalPages
                    _fetchedAddressList.value = result!!
                    _fetchedBuildingsCount.value = result.addresses.size
                    if (result.last) {
                        _isLastPage.value = true
                    }
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
            _isFetchDone.value = true
        }
    }

    fun setBuildingsByFiltering(lessorType: String) {
        Log.e("what is buildingslistlivedata hereeeeeeeeeeee", _buildingListLiveData.value?.size.toString())
        _buildingListLiveData.value?.forEach { building ->
            if (!building.reviews.isNullOrEmpty()) {
                when (building.reviews[0].communcationTendency) {
                    lessorType -> _filteredBuildingList.plusAssign(building)
                }
            }
        }
        _isFetchDone.value = true
    }

    fun setBuildingsByConditions(conditions: ConditionSetModel) {
        val monthly = conditions.dealType == "MONTHLY"
        val deposit = conditions.dealType == "JEONSE"

        _buildingListLiveData.value?.forEach { building ->
            if (!building.reviews.isNullOrEmpty() && !building.buildingDeals.isNullOrEmpty()) {
                if (building.buildingType == conditions.buildingType
                    && building.buildingDeals.hasDealType(conditions.dealType)
                    && building.buildingDeals.isWithinCost(monthly, conditions.monthlyPayStart, conditions.monthlyPayEnd, deposit, conditions.depositPayStart, conditions.depositPayEnd)
                    && building.reviews.hasOptions(conditions.options)) {
                    _filteredBuildingList.plusAssign(building)
                }
            }
        }
    }

    private suspend fun getBuildingInfoById(id: Int) {
        val result = buildingRepository.getBuildingsInfoById(id)
        if (result != null) {
            Log.e("what building is currently being fetched?", result.toString())
            insertBuildingInfoToLocal(result)
            getBuildingInfoFromLocal(id)
        } else {
            getBuildingInfoFromLocal(id)
        }
    }

    private suspend fun getBuildingInfoFromLocal(id: Int) {
        try {
            zeepyLocalRepository.fetchBuildingById(id).collect {
                _buildingListLiveData.plusAssign(it)
                Log.e("FETCHED ==> building", it.toString())
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(newValue: T) {
        if (this.value == null) {
            this.value = mutableListOf(newValue)
        } else {
            val value = this.value
            value?.add(newValue)
            this.value = value
        }
    }

    private suspend fun insertBuildingInfoToLocal(building: BuildingSummaryModel) {
        if (!zeepyLocalRepository.isRowExists(building.id)) {
            zeepyLocalRepository.insertBuilding(building)
            zeepyLocalRepository.insertBuildingDeals(building, building.id)
            zeepyLocalRepository.insertBuildingLikes(building, building.id)
            zeepyLocalRepository.insertBuildingReviews(building, building.id)
            Log.e("INSERTED <== building", building.toString())
        }
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

    private fun fetchAddressListFromLocal() {
        addDisposable(
            zeepyLocalRepository.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ addressList ->
                    _addressList.postValue(Event(addressList))
                    Log.e("addressList", _addressList.toString())
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