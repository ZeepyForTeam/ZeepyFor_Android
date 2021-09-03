package com.zeepy.zeepyforandroid.lookaround.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepository
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LookAroundViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val searchAddressListRepository: SearchAddressListRepository,
    private val buildingRepository: BuildingRepository,
    private val zeepyLocalRepository: ZeepyLocalRepository
): BaseViewModel() {

    private val _addressList = MutableLiveData<List<LocalAddressEntity>>(mutableListOf())
    val addressList: LiveData<List<LocalAddressEntity>>
        get() = _addressList

    private val _selectedAddress = MutableLiveData<LocalAddressEntity>()
    val selectedAddress: LiveData<LocalAddressEntity>
        get() = _selectedAddress

    private val buildingList = ArrayList<BuildingSummaryModel>()
    private val filteredBuildingList = ArrayList<BuildingSummaryModel>()

    private val _buildingListLiveData = MutableLiveData<List<BuildingSummaryModel>>()
    val buildingListLiveData: LiveData<List<BuildingSummaryModel>>
        get() = _buildingListLiveData

    //TODO: Use Room DB for this too
    private var fetchedAddressList = ArrayList<SearchAddressListModel>()

    private val _fetchedBuilding = MutableLiveData<BuildingSummaryModel?>()
    val fetchedBuilding: LiveData<BuildingSummaryModel?>
        get() = _fetchedBuilding


    /**
     * 현재 주소를 기준으로 빌딩 리스트 가져오기
     */
    fun searchBuildingsByAddress(address: String) {
        addDisposable(
            searchAddressListRepository.searchBuildingAddressList(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    saveAddressList(response as ArrayList<SearchAddressListModel>)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun saveAddressList(addressList: ArrayList<SearchAddressListModel>) {
        fetchedAddressList = addressList
    }

    fun getBuildings(address: String) {
        Log.e("fetchedAddlist before api call", fetchedAddressList.toString())
        fetchedAddressList.clear()
        buildingList.clear()
        searchBuildingsByAddress(address)
        Log.e("fetchedAddlist", fetchedAddressList.toString())
        fetchedAddressList.forEach {
            Log.e("next building", "now")
            getBuildingInfoById(it.id)
        }
        _buildingListLiveData.value = buildingList
    }

    fun addBuildingToList(building: BuildingSummaryModel) {
        buildingList.add(building)
    }

    fun getBuildingsByFiltering(lessorType: String) {
        filteredBuildingList.clear()
        buildingList.forEach { building ->
            if (!building.reviews.isNullOrEmpty()) {
                when (building.reviews[0].communcationTendency) {
                    lessorType -> filteredBuildingList.add(building)
                }
            }
        }
        _buildingListLiveData.value = filteredBuildingList
    }

    fun getBuildingInfoById(id: Int) {
        viewModelScope.launch {
            try {
                val result = buildingRepository.getBuildingsInfoById(id)
                if (result != null) {
                    insertBuildingInfoToLocal(result)
                    getBuildingInfoFromLocal(id)
                    Log.e("from if", "if")
                    addBuildingToList(_fetchedBuilding.value!!)
                } else {
                    getBuildingInfoFromLocal(id)
                    Log.e("from else", "else")
                    addBuildingToList(_fetchedBuilding.value!!)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getBuildingInfoFromLocal(id: Int) {
        zeepyLocalRepository.fetchBuildingById(id).collect {
            _fetchedBuilding.value = it
            Log.e("fetchedbuilding value", _fetchedBuilding.value.toString())
        }
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