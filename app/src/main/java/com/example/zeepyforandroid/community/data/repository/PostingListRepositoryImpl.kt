package com.example.zeepyforandroid.community.data.repository

import com.example.zeepyforandroid.community.data.entity.PostingListModel
import com.example.zeepyforandroid.community.data.remote.data.PostingListDataSource
import io.reactivex.Single
import javax.inject.Inject

class PostingListRepositoryImpl @Inject constructor(
    private val dataSource: PostingListDataSource
): PostingListRepository {
    override fun getPostingList(address: String, communityType: String): Single<List<PostingListModel>> =
        dataSource.getPosting(address, communityType).map { list ->
            list.map { data ->
                data.toPostingListModel()
            }
        }
}