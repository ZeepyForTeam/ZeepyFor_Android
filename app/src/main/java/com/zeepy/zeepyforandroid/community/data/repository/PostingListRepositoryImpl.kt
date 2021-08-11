package com.zeepy.zeepyforandroid.community.data.repository

import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import io.reactivex.Single
import javax.inject.Inject

class PostingListRepositoryImpl @Inject constructor(
    private val dataSource: PostingListDataSource
): PostingListRepository {
//    override fun getPostingList(address: String, communityType: String): Single<List<PostingListModel>> =
//        dataSource.getPosting(address, communityType).map { list ->
//            list.map { data ->
//                data.toPostingListModel()
//            }
//        }

    override fun getPostingList(address: String, communityType: String): Single<List<PostingDetailModel>> =
        dataSource.getPosting(address, communityType).map { list ->
            list.map { data ->
                data.toPostingModel()
            }
        }
}