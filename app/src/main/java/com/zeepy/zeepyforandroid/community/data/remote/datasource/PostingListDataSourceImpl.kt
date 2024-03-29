package com.zeepy.zeepyforandroid.community.data.remote.datasource

import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseMyZipList
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingList
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PostingListDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): PostingListDataSource {
    override fun getPosting(address: String, communityType: String?, page: Int?): Single<ResponsePostingList> = zeepyApiService.getCommunityPostingList(address, communityType,page)
    override fun getMyZip(communityCategory: String?): Single<ResponseMyZipList> = zeepyApiService.getCommunityMyZipList(communityCategory)
    override fun getPostingDetail(id: Int): Single<ResponsePostingDetail> = zeepyApiService.fetchPostinDetailcontent(id)
}