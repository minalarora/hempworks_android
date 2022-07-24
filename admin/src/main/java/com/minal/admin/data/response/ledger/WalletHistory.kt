package com.minal.admin.data.response.ledger

import java.util.*

data class WalletHistory(
    val orderid: Long? = null,
    val order: Order? = null,
    val couponid: Long? = null,
    val coupon: Coupon? = null,
    val active: Boolean? = null,
    val wallet: Long? = null,
    val operation: String? = null,
    val amount: Int? = null,
    val who: String? = null,
    val id: Long? = null,
    val date: Date? = null
)