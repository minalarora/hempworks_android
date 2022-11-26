package com.minal.admin.data.response

import com.squareup.moshi.Json

data class ResponsePresList(
    @Json(name = "createdAt")
    var createdAt: String,
    @Json(name = "date")
    var date: String,
    @Json(name = "description")
    var description: String,
    @Json(name = "doctor")
    var doctor: String,
    @Json(name = "_id")
    var _id: String,
    @Json(name = "id")
    var id: Long,
    @Json(name = "prescription")
    var prescription: String? = null,
    @Json(name = "type")
    var type: String? = null,
    @Json(name = "updatedAt")
    var updatedAt: String,
    @Json(name = "__v")
    var v: Int,
    @Json(name = "prescriptions")
    var prescriptions: ArrayList<PrescriptionMedia> = arrayListOf()
)

data class PrescriptionMedia constructor(
    @Json(name = "prescription")
    var prescription: String? = null,
    @Json(name = "type")
    var type: String? = null,
)