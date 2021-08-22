package com.zeepy.zeepyforandroid.community.data.repository

import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import io.reactivex.Single
import javax.inject.Inject

class PostingListRepositoryImpl @Inject constructor(
    private val dataSource: PostingListDataSource
): PostingListRepository {
    override fun getPostingList(address: String, communityType: String?): Single<List<PostingListModel>> =
        dataSource.getPosting(address, communityType).map { it.content.map { it.toPostingListModel() } }

    override fun getMyZipList(communityCategory: String?): Single<List<PostingListModel>> =
        dataSource.getMyZip(communityCategory).map { it.myZip.map { it.toPostingListModel() } }
}