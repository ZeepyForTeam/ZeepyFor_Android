package com.zeepy.zeepyforandroid.community.data.remote.datasource

import com.zeepy.zeepyforandroid.community.data.remote.response.ResponseMyZipList
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class PostingListDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): PostingListDataSource {
    override fun getPosting(address: String, communityType: String?): Single<ResponsePostingList> = zeepyApiService.getCommunityPostingList(address, communityType)
    override fun getMyZip(communityCategory: String?): Single<ResponseMyZipList> = zeepyApiService.getCommunityMyZipList(communityCategory)
    override fun getPostingDetail(id: Int): Single<ResponsePostingDetail> = zeepyApiService.fetchPostinDetailcontent(id)
}