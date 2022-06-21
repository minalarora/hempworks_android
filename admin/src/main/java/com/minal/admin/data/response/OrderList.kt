package com.minal.admin.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

data class OrderList(
    @Json(name = "active")
    var active: Boolean,
    @Json(name = "address")
    var address: Address,
    @Json(name = "addressid")
    var addressid: Long,
    @Json(name = "coupon")
    var coupon: Any,
    @Json(name = "couponid")
    var couponid: Any,
    @Json(name = "createdAt")
    var createdAt: String,
    @Json(name = "date")
    var date: String,
    @Json(name = "discountprice")
    var discountprice: Int,
    @Json(name = "doctorid")
    var doctorid: String,
    @Json(name = "doctorobject")
    var doctorobject: Doctorobject,
    @Json(name = "_id")
    var _id: String,
    @Json(name = "id")
    var id: Long,
    @Json(name = "online")
    var online: Boolean,
    @Json(name = "order")
    var order: List<Order>,
    @Json(name = "payment")
    var payment: String,
    @Json(name = "status")
    var status: String,
    @Json(name = "totalprice")
    var totalprice: Int,
    @Json(name = "transaction")
    var transaction: Any,
    @Json(name = "transactionid")
    var transactionid: Any,
    @Json(name = "updatedAt")
    var updatedAt: String,
    @Json(name = "__v")
    var v: Int,
    @Json(name = "walletprice")
    var walletprice: Int
) {
    @JsonClass(generateAdapter = true)
    data class Address(
        @Json(name = "active")
        var active: Boolean,
        @Json(name = "address1")
        var address1: String,
        @Json(name = "address2")
        var address2: String,
        @Json(name = "city")
        var city: String,
        @Json(name = "createdAt")
        var createdAt: String,
        @Json(name = "date")
        var date: String,
        @Json(name = "doctor")
        var doctor: String,
        @Json(name = "_id")
        var _id: String,
        @Json(name = "id")
        var id: Long,
        @Json(name = "mobile")
        var mobile: String,
        @Json(name = "pincode")
        var pincode: Int,
        @Json(name = "state")
        var state: String,
        @Json(name = "updatedAt")
        var updatedAt: String,
        @Json(name = "__v")
        var v: Int
    )

    @JsonClass(generateAdapter = true)
    data class Doctorobject(
        @Json(name = "cart")
        var cart: Long,
        @Json(name = "certificate")
        var certificate: String,
        @Json(name = "clinic")
        var clinic: String,
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
        var notification: Any,
        @Json(name = "profile")
        var profile: String,
        @Json(name = "status")
        var status: String,
        @Json(name = "updatedAt")
        var updatedAt: String,
        @Json(name = "__v")
        var v: Int,
        @Json(name = "wallet")
        var wallet: Long
    ) {
        @JsonClass(generateAdapter = true)
        data class Discount(
            @Json(name = "_id")
            var id: String,
            @Json(name = "percentage")
            var percentage: Double,
            @Json(name = "price")
            var price: Int
        )
    }

    @JsonClass(generateAdapter = true)
    data class Order(
        @Json(name = "_id")
        var id: String,
        @Json(name = "price")
        var price: Int,
        @Json(name = "productid")
        var productid: Long,
        @Json(name = "productname")
        var productname: String,
        @Json(name = "quantity")
        var quantity: Int,
        @Json(name = "status")
        var status: Any,
        @Json(name = "variantid")
        var variantid: Long,
        @Json(name = "variantname")
        var variantname: String
    )
}