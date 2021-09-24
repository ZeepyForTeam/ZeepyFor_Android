package com.zeepy.zeepyforandroid.community.postingdetail

import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel

interface PostingUserIdxInterface {
    fun getWriterIdx(): CommentAuthenticatedModel
}