package com.example.zeepyforandroid.network.response

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    @field:SerializedName("addresses")
    val addresses: List<AddressesItem?>? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("errorMessage")
    val errorMessage: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class AddressElementsItem(

    @field:SerializedName("types")
    val types: List<String?>? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("shortName")
    val shortName: String? = null,

    @field:SerializedName("longName")
    val longName: String? = null
)

data class AddressesItem(

    @field:SerializedName("distance")
    val distance: Double? = null,

    @field:SerializedName("roadAddress")
    val roadAddress: String? = null,

    @field:SerializedName("x")
    val X: String? = null,

    @field:SerializedName("jibunAddress")
    val jibunAddress: String? = null,

    @field:SerializedName("y")
    val Y: String? = null,

    @field:SerializedName("addressElements")
    val addressElements: List<AddressElementsItem?>? = null,

    @field:SerializedName("englishAddress")
    val englishAddress: String? = null
)

data class Meta(

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("totalCount")
    val totalCount: Int? = null
)
