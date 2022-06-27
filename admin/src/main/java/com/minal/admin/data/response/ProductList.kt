package com.minal.admin.data.response

import com.squareup.moshi.Json

data class ProductList(
    @Json(name = "price")
    var price: Int?=null,
    @Json(name = "productid")
    var productid: Long?=null,
    @Json(name = "quantity")
    var quantity: Int?=null,
    @Json(name = "variantid")
    var variantid: Long?=null,
    var variantName:String?=null,
    var variantSize:Long?=null,
    var variantType:String?=null

    )