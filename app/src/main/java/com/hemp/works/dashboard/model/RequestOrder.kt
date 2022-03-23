package com.hemp.works.dashboard.model


data class RequestOrder(
    val address: Long,
    val payment: String,
    val totalprice: Int,
    val discountprice: Int
)
