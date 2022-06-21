package com.minal.admin.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseSendMsg(
    @Json(name = "adminseen")
    var adminseen: Boolean,
    @Json(name = "createdAt")
    var createdAt: String,
    @Json(name = "doctor")
    var doctor: Any,
    @Json(name = "doctorseen")
    var doctorseen: Boolean,
    @Json(name = "_id")
    var _id: String,
    @Json(name = "id")
    var id: String,
    @Json(name = "messages")
    var messages: List<Message>,
    @Json(name = "uniqueid")
    var uniqueid: String,
    @Json(name = "updatedAt")
    var updatedAt: String,
    @Json(name = "__v")
    var v: Int
) {
    @JsonClass(generateAdapter = true)
    data class Message(
        @Json(name = "date")
        var date: String,
        @Json(name = "_id")
        var id: String,
        @Json(name = "isDoctor")
        var isDoctor: Boolean,
        @Json(name = "message")
        var message: String,
        @Json(name = "type")
        var type: String
    )
}