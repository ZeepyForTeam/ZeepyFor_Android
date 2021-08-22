package com.zeepy.zeepyforandroid.enum

import com.zeepy.zeepyforandroid.R

enum class PostingType(val content: String) {
    JOINTPURCHASE("공동구매"),
    FREESHARING("무료나눔"),
    NEIGHBORHOODFRIEND("동네친구");

    companion object {
        fun findPostingTypeTag(content: String): String {
            return values().find { it.content == content }?.name
                ?: throw IllegalArgumentException("Not found Posting Type Tag")
        }

        fun convertToCommunityTypeString(content: String):String {
            return values().find { it.name == content }?.content
                ?: throw java.lang.IllegalArgumentException("Not Found")
        }
    }
}