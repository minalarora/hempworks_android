package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestLiveSession(
    @Json(name = "title")
    var title: String?=null,
    @Json(name = "url")
    var url: String?=null
)