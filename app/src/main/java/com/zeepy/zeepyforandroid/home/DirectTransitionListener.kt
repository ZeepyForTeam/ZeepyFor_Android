package com.zeepy.zeepyforandroid.home

interface DirectTransitionListener {
    fun applyCommunityFilter(type: String)
    fun applyLookAroundFilter(type: String)
    fun comeBackHome()
}