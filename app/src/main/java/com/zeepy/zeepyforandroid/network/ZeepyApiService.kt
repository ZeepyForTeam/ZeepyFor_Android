package com.zeepy.zeepyforandroid.network


import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.building.BuildingsAllDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestParticipationDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestReportDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseImageUrls
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseMyZipList
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingList
import com.zeepy.zeepyforandroid.imagecontrol.PreSignedUrlDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingLikeDTO
import com.zeepy.zeepyforandroid.myprofile.data.ModifyPasswordReqDTO
import com.zeepy.zeepyforandroid.myprofile.data.ReportRequestDTO
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTOList
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import com.zeepy.zeepyforandroid.signin.dto.response.ResponseNicknameAndEmailDTO
import com.zeepy.zeepyforandroid.signup.RequestSignUpDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ZeepyApiService {

    @POST("/api/user/redundancy/email")
    fun checkEmailRepetition(@Body email: String): Completable

    @POST("/api/user/redundancy/nickname")
    fun checkNickNamRepetition(@Body nickname: String): Completable

    @POST("/api/auth/login")
    fun signin(@Body requestLoginDTO: RequestLoginDTO): Single<ResponseAuthDTO>

    @POST("/api/auth/login/naver")
    fun naverSignin(@Body accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO>

    @POST("/api/auth/login/kakao")
    fun kakaoSignin(@Body accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO>

    @POST("/api/auth/reissue")
    fun fetchAccessToken(@Body reIssueReqDto: RequestTokenDTO): Call<ResponseAuthDTO>

    @POST("/api/review")
    fun writeReview(@Body reviewDto: RequestWriteReview): Completable
    
    @GET("/api/community")
    fun getPostingList(
        @Query("address") address: String,
        @Query("communityType") communityType: String?
    ): Single<List<ResponsePostingList>>

    @GET("/api/user/address")
    fun getAddressList(): Single<AddressListDTO>

    @GET("/api/buildings/address")
    fun getBuildingByAddress(@Query("address") address: String): Single<ResponseBuildingInfoDTO>

    @PUT("/api/user/address")
    fun addAddress(@Body addresses: AddressListDTO): Completable

    @GET("/api/buildings/addresses")
    fun searchBuildingAddress(@Query ("address")address: String): Single<ResponseSearchBuildingAddressDTO>

    @GET("/api/buildings/addresses")
    suspend fun searchBuildingsByAddress(@Query ("address") address: String, @Query ("page") page: Int): Response<ResponseSearchBuildingAddressDTO>

    @GET("/api/buildings/{id}")
    suspend fun getBuildingById(@Path ("id") id: Int): Response<ResponseBuildingInfoDTO>

    @GET("/api/community")
    fun getCommunityPostingList(
        @Query ("address") address: String,
        @Query ("communityType") communityType: String?,
        @Query("page") page: Int?
    ): Single<ResponsePostingList>

    @GET("/api/community/myzip")
    fun getCommunityMyZipList(@Query ("communityCategory") communityCategory: String?): Single<ResponseMyZipList>

    @GET("/api/community/{id}")
    fun fetchPostinDetailcontent(@Path ("id") id: Int): Single<ResponsePostingDetail>

    @GET("/api/buildings/location")
    suspend fun getBuildingsByLocation(
        @Query("latitudeGreater") latitudeGreater: Double,
        @Query("latitudeLess") latitudeLess: Double,
        @Query("longitudeGreater") longitudeGreater: Double,
        @Query("longitudeLess") longitudeLess: Double
    ): Response<List<ResponseBuildingInfoDTO>>

    @DELETE("/api/user/withdrawal")
    suspend fun deleteAccount(): Response<Unit?>

    @DELETE("/api/auth/logout")
    suspend fun logout(): Response<Unit?>

    @GET("/api/user/nickname/email")
    fun getUserNicknameAndEmail(@Query("userEmail") userEmail: String): Single<ResponseNicknameAndEmailDTO>

    @POST("/api/user/registration")
    fun signUp(@Body requestSignUpDTO: RequestSignUpDTO): Completable

    @POST("/api/community")
    fun uploadPosting(@Body requestWritePosting: RequestWritePosting): Completable

    @GET("/api/s3")
    fun getPresignedUrl(): Observable<PreSignedUrlDTO>

    @POST("/api/community/comment/{id}")
    fun postComment(@Path ("id") id: Int, @Body requestCommentRequest: RequestWriteCommentDTO): Completable

    @POST("/api/community/participation/{id}")
    fun participateGroupPurchase(@Path ("id") id: Int, @Body participateDTO: RequestParticipationDTO): Completable

    @POST("/api/community/like")
    fun scrapPosting(@Query ("communityId") communityId: Int): Completable

    @DELETE("/api/community/like")
    fun cancelScrapPosting(@Query ("communityId") communityId: Int): Completable

    @PUT("/api/community/participation/{id}")
    fun cancelParticipation(@Path ("id") communityId: Int): Completable

    @DELETE("/api/community/{id}")
    fun deletePosting(@Path ("id") communityId: Int): Completable

    @Multipart
    @POST("api/s3")
    fun postImages(@Part file: ArrayList<MultipartBody.Part?>): Single<ResponseImageUrls>

    @POST("/api/likes/buildings")
    suspend fun scrapBuilding(@Body buildingLikeRequestDTO: BuildingLikeRequestDTO): Response<Unit?>

    @DELETE("/api/likes/buildings/{id}")
    suspend fun cancelScrapBuilding(@Path ("id") buildingId: Int): Response<Unit?>

    @GET("/api/buildings")
    suspend fun getBuildingsAll(@Query ("page") page: Int): Response<BuildingsAllDTO>

    @GET("/api/review/user")
    suspend fun getUserReviews(): Response<SimpleReviewDTOList>

    @POST("/api/reports")
    fun reportComment(@Body requestReportDTO:RequestReportDTO): Completable

    @GET("/api/buildings/like")
    suspend fun getBuildingsUserLike(@Query ("page") page: Int): Response<BuildingsAllDTO>

    @PUT("/api/user/password")
    suspend fun changeUserPassword(@Body modifyPasswordReqDTO: ModifyPasswordReqDTO): Response<Unit?>
}