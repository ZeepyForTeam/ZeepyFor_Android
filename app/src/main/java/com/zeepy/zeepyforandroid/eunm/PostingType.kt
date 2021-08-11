package com.zeepy.zeepyforandroid.eunm

import com.zeepy.zeepyforandroid.R

enum class PostingType(val content: Int) {
    JOINTPURCHASE(R.string.group_purchasing_community),
    FREESHARING(R.string.free_sharing_community),
    NEIGHBORHOODFRIEND(R.string.friend_community);

    companion object {
        fun findPostingTypeTag(content: Int): String {
            return values().find { it.content == content }?.name
                ?: throw IllegalArgumentException("Not found Posting Type Tag")
        }
    }
}