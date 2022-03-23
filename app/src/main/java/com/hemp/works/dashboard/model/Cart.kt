package com.hemp.works.dashboard.model

import java.util.*

data class Cart(
    val coupon: Coupon? = null,
    val doctor: String? = null,
    val products: List<CartProduct>? = null,
    val id: Long? = null,
    val date: Date? = null,
    val totalprice: Long = 0,
    val discountprice: Long = 0
)
