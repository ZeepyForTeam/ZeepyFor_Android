package com.zeepy.zeepyforandroid.community.data.remote.datasource

import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseMyZipList
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingList
import io.reactivex.Single

interface PostingListDataSource {
    fun getPosting(address: String, communityType: String?) : Single<ResponsePostingList>
    fun getMyZip(communityCategory: String?): Single<ResponseMyZipList>
    fun getPostingDetail(id: Int) : Single<ResponsePostingDetail>
}