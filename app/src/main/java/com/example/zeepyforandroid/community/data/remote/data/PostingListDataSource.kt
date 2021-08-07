package com.example.zeepyforandroid.community.data.remote.data

import com.example.zeepyforandroid.community.data.remote.response.ResponsePostingList
import io.reactivex.Single

interface PostingListDataSource {
    fun getPosting(address: String, communityType: String) : Single<List<ResponsePostingList>>
}