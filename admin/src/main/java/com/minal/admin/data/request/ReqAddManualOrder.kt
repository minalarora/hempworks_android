package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types

@JsonClass(generateAdapter = true)
data class ReqAddManualOrder(
    @Json(name = "date")
    var date: String?=null,
    @Json(name = "doctor")
    var doctor: String?=null,
    @Json(name = "pendingprice")
    var pendingprice: Int?=null,
    @Json(name = "products")
    var products: List<Product>?=null,
    @Json(name = "totalprice")
    var totalprice: Int?=null
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        @Json(name = "price")
        var price: Int?=null,
        @Json(name = "productid")
        var productid: Long?=null,
        @Json(name = "quantity")
        var quantity: Int?=null,
        @Json(name = "variantid")
        var variantid: Long?=null,
    )
}
