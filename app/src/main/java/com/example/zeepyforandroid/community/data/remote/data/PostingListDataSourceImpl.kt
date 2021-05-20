package com.example.zeepyforandroid.community.data.remote.data

import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting
import io.reactivex.Single
import kotlin.random.Random

class PostingListDataSourceImpl: PostingListDataSource {
    override fun getPosting(): Single<List<ResponsePosting>> = Single.just(
        listOf(
            ResponsePosting(
                "https://github.com/SONPYEONGHWA.png",
                "peace",
                "2021.05.16 18:00:00",
                1,
                "Community 만드는 중입니다~~",
                "치킨, 피자, 짜장면, 탕수육, 떡볶이, 삼겹살, 스테이크, 파스타, 라면, 갈비, 회, 조개구이, 매운탕, 보쌈, 족발",
                (1..4).map { UrlPictureModel( "https://picsum.photos/${Random.nextInt(100, 300)}") },
                true,
                50
            )
        )
    )
}