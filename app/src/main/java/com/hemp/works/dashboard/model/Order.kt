package com.hemp.works.dashboard.model

import com.hemp.works.login.data.model.Address
import com.hemp.works.login.data.model.Doctor
import java.util.*

data class Order(
    val status: String? = null,
    val transactionid: Long? = null,
    val transaction: Transaction? = null,
    val couponid: Long? = null,
    val coupon: Coupon? = null,
    val totalprice: Long? = null,
    val discountprice: Long? = null,
    val id: Long? = null,
    val doctorid: String? = null,
    val doctorobject: Doctor? = null,
    val addressid: String? = null,
    val address: Address? = null,
    val payment: String? = null,
    val order: List<OrderProduct>? = null,
    val date: Date? = null
)

data class OrderProduct(
    val productid: Long? = null,
    val variantid: Long? = null,
    val productname: String? = null,
    val variantname: String? = null,
    val quantity: Int? = null,
    val price: Int? = null
)
