package com.example.zeepyforandroid.community.data.remote.data

import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.example.zeepyforandroid.community.postingdetail.CommentModel
import com.example.zeepyforandroid.community.postingdetail.NestedCommentModel
import io.reactivex.Single
import kotlin.random.Random

class PostingListDataSourceImpl : PostingListDataSource {
    override fun getPosting(): Single<List<ResponsePosting>> = Single.just(
        listOf(
            ResponsePosting(
                1,
                "https://github.com/SONPYEONGHWA.png",
                "zzangu99",
                "10분전",
                1,
                "Community 만드는 중입니다~~",
                "치킨, 피자, 짜장면, 탕수육, 떡볶이, 삼겹살, 스테이크, 파스타, 라면, 갈비, 회, 조개구이, 매운탕, 보쌈, 족발",
                true,
                (1..4).map { UrlPictureModel("https://picsum.photos/${Random.nextInt(100, 300)}") },
                false,
                50,
                listOf(
                    CommentModel(
                        13,
                        "https://picsum.photos/${Random.nextInt(100, 300)}",
                        "sson_peace7",
                        "뷰 거의 다 만들어간다!! 날씨는 오지게 더워져가고,,화채가 땡기는 날씨네요. Zeepy 여러분 힘내세요:) ",
                        "2021.05.21",
                        false,
                        listOf(
                            NestedCommentModel(
                                3,
                            "hand peace",
                            "아소토 바지락술찜, 금태 개맛있어요",
                                true,
                            "2021.05.21"
                            ),
                            NestedCommentModel(
                                5,
                                "hansol",
                                "어제 술값 30만원 나옴 ㅋㅋㅋㅋ",
                                true,
                                "2021.05.21"
                            )
                        )
                    ),
                    CommentModel(
                        2,
                        "https://picsum.photos/${Random.nextInt(100, 300)}",
                        "hyeonjong",
                        "서버개발 너무 쉽다ㅋㅋㅋㅋㅋ",
                        "2020.05.22",
                        true,
                        null
                    ),
                    CommentModel(
                        2,
                        "https://picsum.photos/${Random.nextInt(100, 300)}",
                        "hyeonjong",
                        "서버개발 너무 쉽다ㅋㅋㅋㅋㅋ",
                        "2020.05.22",
                        false,
                        null
                    )
                )
            ),
            ResponsePosting(
                1,
                "https://github.com/SONPYEONGHWA.png",
                "zzangu99",
                "15분전",
                1,
                "이것이 커뮤니티다!!!!",
                "5월 29일 10시반 한사랑 산악회 인왕산 등반. 꽃도보고 맑은 공기도 마시고 열정 열정 열정",
                true,
                (1..4).map { UrlPictureModel("https://picsum.photos/${Random.nextInt(100, 300)}") },
                false,
                50,
                null
            )
        )
    )
}