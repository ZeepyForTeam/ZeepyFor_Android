package com.example.zeepyforandroid.community.data.remote.data

import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting
import io.reactivex.Single

interface PostingListDataSource {
    fun getPosting() : Single<List<ResponsePosting>>
}