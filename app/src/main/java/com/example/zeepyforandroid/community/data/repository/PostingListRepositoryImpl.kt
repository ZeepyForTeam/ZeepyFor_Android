package com.example.zeepyforandroid.community.data.repository

import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.remote.data.PostingListDataSource
import io.reactivex.Single
import javax.inject.Inject

class PostingListRepositoryImpl @Inject constructor(
    private val dataSource: PostingListDataSource
): PostingListRepository {
    override fun getPostingList(): Single<List<PostingModel>> =
        dataSource.getPosting().map { list ->
            list.map { data ->
                data.toPostingModel()
            }
        }
}