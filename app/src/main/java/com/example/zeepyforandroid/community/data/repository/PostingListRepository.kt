package com.example.zeepyforandroid.community.data.repository

import com.example.zeepyforandroid.community.data.entity.PostingListModel
import io.reactivex.Single

interface PostingListRepository {
    fun getPostingList(address: String, communityType: String): Single<List<PostingListModel>>
}