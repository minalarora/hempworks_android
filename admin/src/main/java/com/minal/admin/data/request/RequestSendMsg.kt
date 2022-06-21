package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RequestSendMsg(
    @Json(name = "id")
    var id: String?=null,
    @Json(name = "isDoctor")
    var isDoctor: Boolean?=null,
    @Json(name = "message")
    var message: String?=null,
    @Json(name = "type")
    var type: String?=null
)