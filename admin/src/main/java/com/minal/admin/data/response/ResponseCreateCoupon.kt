package com.minal.admin.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseCreateCoupon(
    @Json(name = "active")
    var active: Boolean,
    @Json(name = "createdAt")
    var createdAt: String,
    @Json(name = "date")
    var date: String,
    @Json(name = "_id")
    var _id: String,
    @Json(name = "id")
    var id: Long,
    @Json(name = "type")
    var type: String,
    @Json(name = "updatedAt")
    var updatedAt: String,
    @Json(name = "__v")
    var v: Int,
    @Json(name = "value")
    var value: String
)