package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestCreateCoupon(
    @Json(name = "addproduct")
    var addproduct: Long?=null,
    @Json(name = "addvariant")
    var addvariant: Long?=null,
    @Json(name = "canuse")
    var canuse: Int?=null,
    @Json(name = "description")
    var description: String?=null,
    @Json(name = "name")
    var name: String?=null,
    @Json(name = "product")
    var product: Long?=null,
    @Json(name = "public")
    var `public`: Boolean?=null,
    @Json(name = "type")
    var type: String?=null,
    @Json(name = "value")
    var value: String?=null,
    @Json(name = "variant")
    var variant: Long?=null
)