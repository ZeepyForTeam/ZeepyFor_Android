package com.example.zeepyforandroid.community.data.repository

import com.example.zeepyforandroid.community.data.entity.PostingModel
import io.reactivex.Single

interface PostingListRepository {
    fun getPostingList(): Single<List<PostingModel>>
}