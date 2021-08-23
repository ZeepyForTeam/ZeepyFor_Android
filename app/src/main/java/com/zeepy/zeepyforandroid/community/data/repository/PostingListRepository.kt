package com.zeepy.zeepyforandroid.community.data.repository

import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingDetail
import io.reactivex.Single

interface PostingListRepository {
    fun getPostingList(address: String, communityType: String?): Single<List<PostingListModel>>
    fun getMyZipList(communityCategory: String?): Single<List<PostingListModel>>
    fun getPostingDetail(id: Int): Single<PostingDetailModel>
}