package com.zeepy.zeepyforandroid.community.data.repository

import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import io.reactivex.Single

interface PostingListRepository {
//    fun getPostingList(address: String, communityType: String): Single<List<PostingListModel>>
    fun getPostingList(address: String, communityType: String): Single<List<PostingDetailModel>>
}