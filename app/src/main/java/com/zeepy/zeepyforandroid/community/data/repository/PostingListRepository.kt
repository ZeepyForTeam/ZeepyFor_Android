package com.zeepy.zeepyforandroid.community.data.repository

import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import io.reactivex.rxjava3.core.Single


interface PostingListRepository {
    fun getPostingList(address: String, communityType: String?, page: Int?): Single<List<PostingListModel>>
    fun getMyZipList(communityCategory: String?): Single<List<PostingListModel>>
    fun getPostingDetail(id: Int): Single<PostingDetailModel>
}