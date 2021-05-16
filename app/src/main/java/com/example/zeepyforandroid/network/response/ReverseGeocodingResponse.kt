package com.example.zeepyforandroid.network.response

import com.google.gson.annotations.SerializedName

data class ReverseGeocodingResponse(

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,

    @field:SerializedName("status")
    val status: Status? = null
)

data class Addition4(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null
)

data class Land(

    @field:SerializedName("addition3")
    val addition3: Addition3? = null,

    @field:SerializedName("addition2")
    val addition2: Addition2? = null,

    @field:SerializedName("addition1")
    val addition1: Addition1? = null,

    @field:SerializedName("addition0")
    val addition0: Addition0? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("number1")
    val number1: String? = null,

    @field:SerializedName("number2")
    val number2: String? = null,

    @field:SerializedName("addition4")
    val addition4: Addition4? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Area4(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Code(

    @field:SerializedName("mappingId")
    val mappingId: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)

data class Addition1(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null
)

data class Area2(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Status(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Area3(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Coords(

    @field:SerializedName("center")
    val center: Center? = null
)

data class Center(

    @field:SerializedName("crs")
    val crs: String? = null,

    @field:SerializedName("x")
    val X: Double? = null,

    @field:SerializedName("y")
    val Y: Double? = null
)

data class Addition3(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null
)

data class Area0(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Region(

    @field:SerializedName("area1")
    val area1: Area1? = null,

    @field:SerializedName("area2")
    val area2: Area2? = null,

    @field:SerializedName("area3")
    val area3: Area3? = null,

    @field:SerializedName("area4")
    val area4: Area4? = null,

    @field:SerializedName("area0")
    val area0: Area0? = null
)

data class ResultsItem(

    @field:SerializedName("code")
    val code: Code? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("land")
    val land: Land? = null,

    @field:SerializedName("region")
    val region: Region? = null
) {
    fun convertAddress(): String? {
        var address: String? = ""

        region?.run {
            if (!area1?.name.isNullOrEmpty()) {
                address += area1?.name
            }

            if (!area2?.name.isNullOrEmpty()) {
                address += " ${area2?.name}"
            }

            if (!area3?.name.isNullOrEmpty()) {
                address += " ${area3?.name}"
            }

            if (!area4?.name.isNullOrEmpty()) {
                address += " ${area4?.name}"
            }

            land?.run {
                if (!name.isNullOrEmpty()) {
                    address += " ${name}"
                }

                if (!number1.isNullOrEmpty()) {
                    address += " ${number1}"
                }

                if (!number2.isNullOrEmpty()) {
                    address += "-${number2}"
                }

                addition1?.run {
                    if (!value.isNullOrEmpty()) {
                        address += "\n우편번호 : $value"
                    }
                }
            }
        }

        return address
    }
}


data class Area1(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("alias")
    val alias: String? = null,

    @field:SerializedName("coords")
    val coords: Coords? = null
)

data class Addition0(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null
)

data class Addition2(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null
)
