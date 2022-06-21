package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqAddCreditManual(
    @Json(name = "amount")
    var amount: Int?=null,
    @Json(name = "date")
    var date: String?=null,
    @Json(name = "doctor")
    var doctor: String?=null
)