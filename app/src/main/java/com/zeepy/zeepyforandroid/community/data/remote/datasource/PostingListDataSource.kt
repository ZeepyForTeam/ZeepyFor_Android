package com.zeepy.zeepyforandroid.community.data.remote.datasource

import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import io.reactivex.Single

interface PostingListDataSource {
//    fun getPosting(address: String, communityType: String) : Single<List<ResponsePostingList>>
    fun getPosting(address: String, communityType: String?) : Single<ResponsePostingList>
}