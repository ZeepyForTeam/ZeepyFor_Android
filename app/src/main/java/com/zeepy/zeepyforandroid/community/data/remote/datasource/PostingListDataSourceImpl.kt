package com.zeepy.zeepyforandroid.community.data.remote.datasource

import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.entity.NestedCommentModel
import com.zeepy.zeepyforandroid.community.data.entity.UrlPictureModel
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject
import kotlin.random.Random

class PostingListDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): PostingListDataSource {
  override fun getPosting(address: String, communityType: String?): Single<ResponsePostingList> = zeepyApiService.getCommunityPostingList(address, communityType)
}