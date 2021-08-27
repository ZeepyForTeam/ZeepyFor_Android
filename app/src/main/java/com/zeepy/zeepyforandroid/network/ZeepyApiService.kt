package com.zeepy.zeepyforandroid.network

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponseMyZipList
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.signin.dto.RequestLogin
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ZeepyApiService {

    @POST("/api/user/redundancy/email")
    fun checkEmailRepetition(@Body email: String): Completable

    @POST("/api/user/redundancy/nickname")
    fun checkNickNamRepetition(@Body nickname: String): Completable

    @POST("/api/auth/login")
    fun signin(@Body requestLogin: RequestLogin): Single<ResponseAuthDTO>

    @POST("/api/auth/reissue")
    fun fetchAccessToken(@Body reIssueReqDto: RequestTokenDTO): Response<ResponseAuthDTO>

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

    @GET("/api/community")
    fun getCommunityPostingList(@Query ("address") address: String, @Query ("communityType") communityType: String?): Single<ResponsePostingList>

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

//    @DELETE("api/user/withdrawal")
//    suspend fun deleteAccount(@Body userEmail: String): Response<>
}