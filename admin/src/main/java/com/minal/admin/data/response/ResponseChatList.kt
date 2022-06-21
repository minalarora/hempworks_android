package com.minal.admin.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseChatList(
    @Json(name = "adminseen")
    var adminseen: Boolean,
    @Json(name = "createdAt")
    var createdAt: String,
    @Json(name = "doctor")
    var doctor: Doctor,
    @Json(name = "doctorseen")
    var doctorseen: Boolean,
    @Json(name = "_id")
    var _id: String,
    @Json(name = "id")
    var id: String,
    @Json(name = "messages")
    var messages: Messages,
    @Json(name = "uniqueid")
    var uniqueid: String,
    @Json(name = "updatedAt")
    var updatedAt: String,
    @Json(name = "__v")
    var v: Int
) {
    data class Doctor(
        @Json(name = "cart")
        var cart: Long,
        @Json(name = "certificate")
        var certificate: String,
        @Json(name = "clinic")
        var clinic: String,
        @Json(name = "course")
        var course: String,
        @Json(name = "createdAt")
        var createdAt: String,
        @Json(name = "credit")
        var credit: Long,
        @Json(name = "discount")
        var discount: List<Discount>,
        @Json(name = "email")
        var email: String,
        @Json(name = "_id")
        var _id: String,
        @Json(name = "id")
        var id: String,
        @Json(name = "mobile")
        var mobile: String,
        @Json(name = "name")
        var name: String,
        @Json(name = "notification")
        var notification: String,
        @Json(name = "password")
        var password: String,
        @Json(name = "profile")
        var profile: String,
        @Json(name = "status")
        var status: String,
        @Json(name = "tokens")
        var tokens: List<Token>,
        @Json(name = "updatedAt")
        var updatedAt: String,
        @Json(name = "__v")
        var v: Int,
        @Json(name = "wallet")
        var wallet: Long
    ){
        data class Discount(
            @Json(name = "_id")
            var id: String,
            @Json(name = "percentage")
            var percentage: Double,
            @Json(name = "price")
            var price: Int
        )

        data class Token(
            @Json(name = "_id")
            var id: String,
            @Json(name = "token")
            var token: String
        )
    }

    data class Messages(
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