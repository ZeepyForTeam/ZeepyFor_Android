package com.example.zeepyforandroid.community.data.remote.data

import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.example.zeepyforandroid.community.postingdetail.CommentModel
import com.example.zeepyforandroid.community.postingdetail.NestedCommentModel
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
                50,
                COMMENTS
            )
        )
    )
    companion object {
        private val COMMENTS = listOf<CommentModel>(
            CommentModel(
                "https://picsum.photos/${Random.nextInt(100, 300)}",
                "sson_peace7",
                "뷰 거의 다 만들어간다,,,!!! Zeepy 개발 너무 재밌어ㅎㅎㅎ ",
                "2021.05.21",
                listO
            ),
            CommentModel(
                "https://picsum.photos/${Random.nextInt(100, 300)}",
                "sson_peace7",
                "Shout Our Passion Together! Plan, Design, Server, iOS, Android, Web 모두 도전하세요 ",
                "2021.05.21"
            ),
            CommentModel(
                "https://picsum.photos/${Random.nextInt(100, 300)}",
                "sson_peace7",
                "치킨, 피자, 짜장면, 탕수육, 떡볶이, 삼겹살, 스테이크, 파스타, 라면, 갈비, 회, 조개구이, 매운탕, 보쌈, 족발 ",
                "2021.05.21"
            )
        )

        private val NESTED_COMMENTS = listOf<NestedCommentModel>(
            NestedCommentModel(
                "hand_peace",
                "이것이 대댓글이다.",
                "2021.05.21"
            ),
            NestedCommentModel(
                "hansol",
                "나 안드로이드로 전향함 수고ㅎㅎ",
                "2021.05.21"
            ),
            NestedCommentModel(
                "루메린",
                "내가 다음 회식 쏠게!!!",
                "2021.05.21"
            )
        )
    }
}